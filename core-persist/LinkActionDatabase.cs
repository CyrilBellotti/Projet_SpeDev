using MySql.Data.MySqlClient;
    
class LinkActionDatabase {
    
    private string serverIp = "172.20.10.9";
    private string username = "root";
    private string password = "";
    private string databaseName = "dbtestcalculation";

    public void PutInDatabase()
    {

        string dbConnectionString = string.Format("server={0};uid={1};pwd={2};database={3};", serverIp, username, password, databaseName);

        MySqlCommand cmd = new MySqlCommand();
        MySqlConnection conn = new MySqlConnection(dbConnectionString);
        cmd.Connection = conn;
        conn.Open();

        // cmd.CommandText = "INSERT INTO stats2(id,sensor_id) VALUES(?id,?sensor_id)";
        cmd.CommandText = "SELECT * FROM sensors WHERE uid = ";
        cmd.CommandText = "INSERT INTO stats2(id,sensor_id) VALUES(?id,?sensor_id)";
        cmd.Parameters.Add("?id", MySqlDbType.VarChar).Value = "99";
        cmd.Parameters.Add("?sensor_id", MySqlDbType.VarChar).Value = "11111111";
        cmd.ExecuteNonQuery();

    }
    public void GetFromDataBase()
    {

        string dbConnectionString = string.Format("server={0};uid={1};pwd={2};database={3};", serverIp, username, password, databaseName);
        string query = "SELECT * FROM stats2";

        var conn = new MySqlConnection(dbConnectionString);
        conn.Open();

        var cmd = new MySqlCommand(query, conn);
        var reader = cmd.ExecuteReader();

        int i = 0;
        while (reader.Read())
        {
            var someValue = reader["sensor_id"];
            i++;
        }

    }
}
