package DataAccess;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @Author: Technical University of Cluj-Napoca, Romania Distributed Systems
 *          Research Laboratory, http://dsrl.coned.utcluj.ro/
 * @Since: Apr 03, 2017
 * @Source http://www.java-blog.com/mapping-javaobjects-database-reflection-generics
 */

public class AbstractDAO<T> {
	protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());

	private final Class<T> type;

	@SuppressWarnings("unchecked")
	public AbstractDAO() {
		this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	private String createSelectQuery(String field) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ");
		sb.append(" * ");
		sb.append(" FROM ");
		sb.append(type.getSimpleName());
		sb.append(" WHERE " + field + " =?");
		return sb.toString();
	}

	public List<T> findAll() {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		String query = "SELECT * FROM " + type.getSimpleName();

		try {
			connection = DatabaseConnection.getConnection();
			statement = connection.prepareStatement(query);
			resultSet = statement.executeQuery();

			return createObjects(resultSet);
		} catch (SQLException ex) {
			Logger.getLogger(AbstractDAO.class.getName()).log(Level.SEVERE, null, ex);
		} finally {
			DatabaseConnection.close(resultSet);
			DatabaseConnection.close(statement);
			DatabaseConnection.close(connection);
		}

		return null;
	}

	public T findById(int id) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		String query = createSelectQuery("id");
		try {
			connection = DatabaseConnection.getConnection();
			statement = connection.prepareStatement(query);
			statement.setInt(1, id);
			resultSet = statement.executeQuery();

			return createObjects(resultSet).get(0);
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
		} finally {
			DatabaseConnection.close(resultSet);
			DatabaseConnection.close(statement);
			DatabaseConnection.close(connection);
		}
		return null;
	}

	public T insert(T t) { //to check
		Connection connection = null;
		PreparedStatement statement = null;

		try {
			connection = DatabaseConnection.getConnection();

			Field[] fields = type.getDeclaredFields();
			StringBuilder query = new StringBuilder();
			query.append("INSERT INTO `").append(type.getSimpleName()).append("` (");

			StringBuilder valuesPart = new StringBuilder("VALUES (");
			List<Object> values = new ArrayList<>();

			boolean first = true;
			for (Field field : fields) {
				field.setAccessible(true);

				// Skip 'id' field (assumed to be auto-increment primary key)
				if (field.getName().equalsIgnoreCase("id")) continue;

				if (!first) {
					query.append(", ");
					valuesPart.append(", ");
				}
				query.append("`").append(field.getName()).append("`");
				valuesPart.append("?");
				values.add(field.get(t));

				first = false;
			}

			query.append(") ").append(valuesPart).append(")");

			statement = connection.prepareStatement(query.toString(), Statement.RETURN_GENERATED_KEYS);

			for (int i = 0; i < values.size(); i++) {
				statement.setObject(i + 1, values.get(i));
			}

			System.out.println(type.getSimpleName());
			System.out.println(query);
			statement.executeUpdate();

			ResultSet generatedKeys = statement.getGeneratedKeys();
			if (generatedKeys.next()) {
				Field idField = type.getDeclaredField("id");
				idField.setAccessible(true);

				Object key = generatedKeys.getObject(1);

				if(key instanceof Number) {
					idField.set(t, ((Number)key).intValue());
				} else {
					throw new IllegalStateException("Generated key is not a number: " + key);
				}
			}

		} catch (SQLException | IllegalAccessException | NoSuchFieldException e) {
			//LOGGER.log(Level.WARNING, type.getName() + "DAO:insert " + e.getMessage());
			e.printStackTrace();
		} finally {
			DatabaseConnection.close(statement);
			DatabaseConnection.close(connection);
		}

		return t;
	}

	public T update(T t) { //to check
		Connection connection = null;
		PreparedStatement statement = null;

		try {
			connection = DatabaseConnection.getConnection();

			Field[] fields = type.getDeclaredFields();
			StringBuilder query = new StringBuilder();
			query.append("UPDATE ").append(type.getSimpleName()).append(" SET ");

			List<Object> values = new ArrayList<>();
			Object idValue = null;

			for (int i = 0; i < fields.length; i++) {
				fields[i].setAccessible(true);
				if (fields[i].getName().equalsIgnoreCase("id")) {
					idValue = fields[i].get(t);
					continue;
				}
				if (!values.isEmpty()) query.append(", ");
				query.append(fields[i].getName()).append("=?");
				values.add(fields[i].get(t));
			}

			query.append(" WHERE id=?");
			values.add(idValue);  // for WHERE clause

			statement = connection.prepareStatement(query.toString());

			for (int i = 0; i < values.size(); i++) {
				statement.setObject(i + 1, values.get(i));
			}

			statement.executeUpdate();

		} catch (SQLException | IllegalAccessException e) {
			LOGGER.log(Level.WARNING, type.getName() + "DAO:update " + e.getMessage());
		} finally {
			DatabaseConnection.close(statement);
			DatabaseConnection.close(connection);
		}

		return t;
	}

	private List<T> createObjects(ResultSet resultSet) {
		List<T> list = new ArrayList<T>();
		Constructor[] ctors = type.getDeclaredConstructors();
		Constructor ctor = null;
		for (int i = 0; i < ctors.length; i++) {
			ctor = ctors[i];
			if (ctor.getGenericParameterTypes().length == 0)
				break;
		}
		try {
			while (resultSet.next()) {
				ctor.setAccessible(true);
				T instance = (T)ctor.newInstance();
				for (Field field : type.getDeclaredFields()) {
					String fieldName = field.getName();
					Object value = resultSet.getObject(fieldName);
					PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fieldName, type);
					Method method = propertyDescriptor.getWriteMethod();


					if (value != null) {
						Class<?> paramType = method.getParameterTypes()[0];

						// Safe conversion for common types
						if (paramType == int.class || paramType == Integer.class)
							value = ((Number) value).intValue();
						else if (paramType == double.class || paramType == Double.class)
							value = ((Number) value).doubleValue();
						else if (paramType == boolean.class || paramType == Boolean.class) {
							if (value instanceof Number)
								value = ((Number) value).intValue() != 0;
						} else if (paramType == String.class)
							value = value.toString();
						// Add more type conversions as needed
					}

					method.invoke(instance, value);
				}
				list.add(instance);
			}
		} catch (InstantiationException | IllegalAccessException | SecurityException | IllegalArgumentException |
                 InvocationTargetException | SQLException | IntrospectionException e) {
			e.printStackTrace();
		}

        return list;
	}
}
