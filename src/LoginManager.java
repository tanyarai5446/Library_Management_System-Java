import java.sql.*;
// ADMIN V/S STUDENT ACCESS
public class LoginManager {
    public String verifyLogin(String username, String password) {
        String query = "SELECT user_type FROM login WHERE user_name = ? AND password = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, username);//takes username and replaces 1st ?.
            pstmt.setString(2, password);//takes pass and replaces 2nd ?.
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getString("user_type"); // Returns 'ADMIN' or 'STUDENT'
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null; // Login failed
    }
}
