package app;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.properties.EncryptableProperties;
import services.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ApplicationRunner {
    private String serverUsername = null;
    private String serverPassword = null;
    private DatabaseConnectionService dbService = null;
    private EncryptionService es = new EncryptionService();
    protected CharacterService characterService;
    protected TeamService teamService;
    protected ItemService itemService;
    protected AddService addService;

    public void runApplication(String[] args) {
        Properties props = this.loadProperties();
        this.serverUsername = props.getProperty("serverUsername");
        this.serverPassword = props.getProperty("serverPassword");
        this.dbService = new DatabaseConnectionService(props.getProperty("serverName"), props.getProperty("databaseName"));
        final UserService userService = new UserService(this.dbService);

        if (!this.dbService.connect(this.serverUsername, this.serverPassword)) {
            System.out.println("Database connection failed");
        } else {
            System.out.println("Database Connected!");
            this.loginSucceeded();
        }
    }

    public void loginSucceeded() {
        this.characterService = new CharacterService(dbService);
        this.teamService = new TeamService(dbService);
        this.itemService = new ItemService(dbService);
        this.addService = new AddService(dbService);
    }

    public Properties loadProperties() {
        String binDir = System.getProperty("user.dir") + "/out/" + "/production/" + "/SimulatorProject/" + "/app/";
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword(this.es.getEncryptionPassword());
        FileInputStream fis = null;
        EncryptableProperties props = new EncryptableProperties(encryptor);

        try {
            fis = new FileInputStream(binDir + "simulatorapp.properties");
            props.load(fis);
        } catch (FileNotFoundException var16) {
            System.out.println("template.properties file not found");
            var16.printStackTrace();
            System.exit(1);
        } catch (IOException var17) {
            System.out.println("template.properties file could not be opened");
            var17.printStackTrace();
            System.exit(1);
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException var15) {
                    System.out.println("Input Stream could not be closed.");
                    var15.printStackTrace();
                }
            }

        }

        return props;
    }
}
