package org.yearup.data.mysql;

import org.springframework.stereotype.Component;
import org.yearup.data.ShoppingCartDao;
import org.yearup.models.Product;
import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.math.BigDecimal;

@Component
public class MySqlShoppingCartDao extends MySqlDaoBase implements ShoppingCartDao
{
    public MySqlShoppingCartDao(DataSource dataSource)
    {
        super(dataSource);
    }

    @Override
    public ShoppingCart getByUserId(int userId)
    {
        ShoppingCart cart = new ShoppingCart();
        
        String sql = """
            SELECT sc.user_id, sc.product_id, sc.quantity,
                   p.product_id, p.name, p.price, p.category_id, p.description, p.color, p.image_url, p.stock, p.featured
            FROM shopping_cart sc
            INNER JOIN products p ON sc.product_id = p.product_id
            WHERE sc.user_id = ?
            """;

        try (Connection connection = getConnection())
        {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, userId);

            ResultSet row = statement.executeQuery();

            while (row.next())
            {
                ShoppingCartItem item = new ShoppingCartItem();
                
                // Create product from result set
                Product product = mapRow(row);
                item.setProduct(product);
                item.setQuantity(row.getInt("quantity"));
                
                cart.add(item);
            }
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }

        return cart;
    }

    @Override
    public void addToCart(int userId, int productId)
    {
        // First check if item already exists in cart
        String checkSql = "SELECT quantity FROM shopping_cart WHERE user_id = ? AND product_id = ?";
        String insertSql = "INSERT INTO shopping_cart (user_id, product_id, quantity) VALUES (?, ?, 1)";
        String updateSql = "UPDATE shopping_cart SET quantity = quantity + 1 WHERE user_id = ? AND product_id = ?";

        try (Connection connection = getConnection())
        {
            // Check if item exists
            PreparedStatement checkStatement = connection.prepareStatement(checkSql);
            checkStatement.setInt(1, userId);
            checkStatement.setInt(2, productId);
            
            ResultSet rs = checkStatement.executeQuery();
            
            if (rs.next())
            {
                // Item exists, increment quantity
                PreparedStatement updateStatement = connection.prepareStatement(updateSql);
                updateStatement.setInt(1, userId);
                updateStatement.setInt(2, productId);
                updateStatement.executeUpdate();
            }
            else
            {
                // Item doesn't exist, insert new
                PreparedStatement insertStatement = connection.prepareStatement(insertSql);
                insertStatement.setInt(1, userId);
                insertStatement.setInt(2, productId);
                insertStatement.executeUpdate();
            }
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateCartItem(int userId, int productId, int quantity)
    {
        if (quantity <= 0)
        {
            // Remove item if quantity is 0 or negative
            String deleteSql = "DELETE FROM shopping_cart WHERE user_id = ? AND product_id = ?";
            
            try (Connection connection = getConnection())
            {
                PreparedStatement statement = connection.prepareStatement(deleteSql);
                statement.setInt(1, userId);
                statement.setInt(2, productId);
                statement.executeUpdate();
            }
            catch (SQLException e)
            {
                throw new RuntimeException(e);
            }
        }
        else
        {
            // Update quantity
            String updateSql = "UPDATE shopping_cart SET quantity = ? WHERE user_id = ? AND product_id = ?";
            
            try (Connection connection = getConnection())
            {
                PreparedStatement statement = connection.prepareStatement(updateSql);
                statement.setInt(1, quantity);
                statement.setInt(2, userId);
                statement.setInt(3, productId);
                statement.executeUpdate();
            }
            catch (SQLException e)
            {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void clearCart(int userId)
    {
        String sql = "DELETE FROM shopping_cart WHERE user_id = ?";

        try (Connection connection = getConnection())
        {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, userId);
            statement.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    protected static Product mapRow(ResultSet row) throws SQLException
    {
        int productId = row.getInt("product_id");
        String name = row.getString("name");
        BigDecimal price = row.getBigDecimal("price");
        int categoryId = row.getInt("category_id");
        String description = row.getString("description");
        String color = row.getString("color");
        int stock = row.getInt("stock");
        boolean isFeatured = row.getBoolean("featured");
        String imageUrl = row.getString("image_url");

        Product product = new Product()
        {{
            setProductId(productId);
            setName(name);
            setPrice(price);
            setCategoryId(categoryId);
            setDescription(description);
            setColor(color);
            setStock(stock);
            setFeatured(isFeatured);
            setImageUrl(imageUrl);
        }};

        return product;
    }
} 
