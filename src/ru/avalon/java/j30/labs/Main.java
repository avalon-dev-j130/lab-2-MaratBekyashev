package ru.avalon.java.j30.labs;

import java.sql.*;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Properties;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.IIOException;


/**
 * Лабораторная работа №3
 * <p>
 * Курс: "DEV-OCPJP. Подготовка к сдаче сертификационных экзаменов серии Oracle Certified Professional Java Programmer"
 * <p>
 * Тема: "JDBC - Java Database Connectivity" 
 *
 * @author Daniel Alpatov <danial.alpatov@gmail.com>
 */
public class Main {

   
    /**
     * Точка входа в приложение
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException {
        /*
         * TODO #01 Подключите к проекту все библиотеки, необходимые для соединения с СУБД.
         */
        try (Connection connection = getConnection()) {
            ProductCode code = new ProductCode("MO", 'N', "Movies");
            code.save(connection);
            printAllCodes(connection);

            //code.setCode("MV");
            //code.save(connection);
            //printAllCodes(connection);
        }
        /*
         * TODO #14 Средствами отладчика проверьте корректность работы программы
         */
    }
    /**
     * Выводит в кодсоль все коды товаров
     * 
     * @param connection действительное соединение с базой данных
     * @throws SQLException 
     */    
    private static void printAllCodes(Connection connection) throws SQLException {
        Collection<ProductCode> codes = ProductCode.all(connection);
        for (ProductCode code: codes) {
            System.out.println(code);
        }
    }
    /**
     * Возвращает URL, описывающий месторасположение базы данных
     * TODO #02 Реализуйте метод getUrl
     * @return URL в виде объекта класса {@link String}
     */
    private static String getUrl() {
        return "jdbc:derby://localhost:1527/sample";
     }
    /**
     * Возвращает параметры соединения
     * TODO #03 Реализуйте метод getProperties
     * @return Объект класса {@link Properties}, содержащий параметры user и 
     * password
     */
    private static Properties getProperties() throws IOException{
        Properties properties = new Properties();
        
        try (InputStream stream = ClassLoader.getSystemResourceAsStream("resources/database.properties")) {
            properties.load(stream);
        } 
        catch (IOException ex) {
            System.out.println(ex.getLocalizedMessage());
            throw new IOException("very complicated exception", ex);
           }
        return properties;
    }
    /**
     * Возвращает соединение с базой данных Sample
     * TODO #04 Реализуйте метод getConnection
     * @return объект типа {@link Connection}
     * @throws SQLException 
     */
    private static Connection getConnection() throws SQLException {
        Properties props;
        try {
          props = getProperties();
        } catch(IOException ex) {
          throw new SQLException("Cannot to determine connection properties", ex);
        }

        return DriverManager.getConnection(getUrl(), 
                                           props.getProperty("database.user"), 
                                           props.getProperty("database.password"));
        

    }
    
}
