package ru.avalon.java.j30.labs;

import java.sql.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Objects;

/**
 * Класс описывает представление о коде товара и отражает соответствующую 
 * таблицу базы данных Sample (таблица PRODUCT_CODE).
 * 
 * @author Daniel Alpatov <danial.alpatov@gmail.com>
 */
public class ProductCode {
    // Код товара
    private String code;
     // Кода скидки
    private char discountCode;
    // Описание
    private String description;

    // Основной конструктор типа {@link ProductCode} 
    public ProductCode(String code, char discountCode, String description) {
        this.code = code;
        this.discountCode = discountCode;
        this.description = description;
    }
    /**
     * Инициализирует объект значениями из переданного {@link ResultSet}
     * 
     * @param set {@link ResultSet}, полученный в результате запроса, 
     * содержащего все поля таблицы PRODUCT_CODE базы данных Sample.
     * TODO #05 реализуйте конструктор класса ProductCode     
     */
    private ProductCode(ResultSet set) throws SQLException{
        this(set.getString("prod_code"), 
             set.getString("discount_code").charAt(0), 
             set.getString("description"));    
    }
    //Возвращает код товара * @return Объект типа {@link String}
    public String getCode() {
        return this.code;
    }
    // Устанавливает код товара @param code код товара
    public void setCode(String code) {
        this.code = code;
    }
    // Возвращает код скидки @return Объект типа {@link String}
    public char getDiscountCode() {
        return this.discountCode;
    }
    // Устанавливает код скидки @param discountCode код скидки
    public void setDiscountCode(char discountCode) {
        this.discountCode = discountCode;
    }
    // Возвращает описание * @return Объект типа {@link String}
    public String getDescription() {
        return description;
    }
    //Устанавливает описание * @param description описание
    public void setDescription(String description) {
        this.description = description;
    }
    /**
     * Хеш-функция типа {@link ProductCode}.
     * 
     * @return Значение хеш-кода объекта типа {@link ProductCode}
     */
    @Override
    /*
    * TODO #06 Реализуйте метод hashCode
    */
    public int hashCode() {
        return Objects.hash(this.code);
    }
    /**
     * Сравнивает некоторый произвольный объект с текущим объектом типа 
     * TODO #07 Реализуйте метод equals
     * {@link ProductCode}
     * 
     * @param obj Объект, скоторым сравнивается текущий объект.
     * @return true, если объект obj тождественен текущему объекту. В обратном 
     * случае - false.
     */
    @Override
    public boolean equals(Object obj) {
        return this.code.equals(((ProductCode)obj).getCode());
    }
    /**
     * Возвращает строковое представление кода товара.
     * TODO #08 Реализуйте метод toString
     * @return Объект типа {@link String}
     */
    @Override
    public String toString() {
       return "Product_code="+ this.code+ ", Discount_code="+this.discountCode+", Description="+this.description;
    }
    /**
     * Возвращает запрос на выбор всех записей из таблицы PRODUCT_CODE 
     * базы данных Sample
         * TODO #09 Реализуйте метод getSelectQuery
     * @param connection действительное соединение с базой данных
     * @return Запрос в виде объекта класса {@link PreparedStatement}
     */
    public static PreparedStatement getSelectQuery(Connection connection) throws SQLException {
        String query = "select prod_code, discount_code, description from product_code";
        PreparedStatement stmt = connection.prepareStatement(query);
        return stmt;
    }
    /**
     * Возвращает запрос на добавление записи в таблицу PRODUCT_CODE 
     * базы данных Sample
         * TODO #10 Реализуйте метод getInsertQuery
     * @param connection действительное соединение с базой данных
     * @return Запрос в виде объекта класса {@link PreparedStatement}
     */
    public static PreparedStatement getInsertQuery(Connection connection) throws SQLException {
        String query = "insert into product_code(prod_code, discount_code, description) values (?,?,?)";
        PreparedStatement stmt = connection.prepareStatement(query);
        return stmt;
    }
    /**
     * Возвращает запрос на обновление значений записи в таблице PRODUCT_CODE 
     * базы данных Sample
         * TODO #11 Реализуйте метод getUpdateQuery
     * @param connection действительное соединение с базой данных
     * @return Запрос в виде объекта класса {@link PreparedStatement}
     */
    public static PreparedStatement getUpdateQuery(Connection connection) throws SQLException {
        String query = "update product_code set discount_code = ?, description = ? where prod_code= ?";
        PreparedStatement stmt = connection.prepareStatement(query);
        return stmt;
    }
    /**
     * Преобразует {@link ResultSet} в коллекцию объектов типа {@link ProductCode}
     * 
     * @param set {@link ResultSet}, полученный в результате запроса, содержащего 
     * все поля таблицы PRODUCT_CODE базы данных Sample
     * @return Коллекция объектов типа {@link ProductCode}
     * @throws SQLException 
     */
    public static Collection<ProductCode> convert(ResultSet set) throws SQLException {
        Collection<ProductCode> tmpList = new LinkedList<>();
        while (set.next()) {
            ProductCode item = new ProductCode(set);
            tmpList.add(item);
        }
        return new ArrayList<>(tmpList);
    }
    /**
     * Сохраняет текущий объект в базе данных. 
         * TODO #13 Реализуйте метод convert
     * <p>
     * Если запись ещё не существует, то выполняется запрос типа INSERT.
     * <p>
     * Если запись уже существует в базе данных, то выполняется запрос типа UPDATE.
     * 
     * @param connection действительное соединение с базой данных
     */
    public void save(Connection connection) throws SQLException {
        PreparedStatement stmt;
        Collection<ProductCode> allItems = all(connection);
        if (allItems.contains(this)) {
          // String query = "update product_code set discount_code = ?, description = ? where prod_code= ?";
          stmt = getUpdateQuery(connection);
          stmt.setString(1, Character.toString(this.discountCode));
          stmt.setString(2, this.description);
          stmt.setString(3, this.code);
          stmt.execute();
        }
        else {
          // String query = "insert into product_code(prod_code, discount_code, description) values (?,?,?)";
          stmt = getInsertQuery(connection);
          stmt.setString(1, this.code);
          stmt.setString(2, Character.toString(this.discountCode));
          stmt.setString(3, this.description);
          stmt.execute();
        };
    }
    /**
     * Возвращает все записи таблицы PRODUCT_CODE в виде коллекции объектов
     * типа {@link ProductCode}
     * 
     * @param connection действительное соединение с базой данных
     * @return коллекция объектов типа {@link ProductCode}
     * @throws SQLException 
     */
    public static Collection<ProductCode> all(Connection connection) throws SQLException {
        try (PreparedStatement statement = getSelectQuery(connection)) {
            try (ResultSet result = statement.executeQuery()) {
                return convert(result);
            }
        }
    }
}
