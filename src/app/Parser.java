package app;

import services.AddClearService;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Parser {

    public static void parse(File file, AddClearService serv) throws FileNotFoundException {
        Scanner titleScan = new Scanner(file);
        titleScan.useDelimiter(",");
        String title = titleScan.next();
        switch (title) {
            case "Abilities":
                parseAbilities(file, serv);
                break;
            case "Items":
                parseItems(file, serv);
                break;
            case "Users":
                parseUsers(file, serv);
                break;
            case "Teams":
                parseTeams(file, serv);
                break;
            case "Characters":
                parseCharacters(file, serv);
                break;
            case "Battles":
                parseBattles(file, serv);
                break;
            case "HasItem":
                parseHasItem(file, serv);
                break;
            case "KnowsAbility":
                parseKnowsAbility(file, serv);
                break;
            case "FoughtBy":
                parseFoughtBy(file, serv);
                break;
        }
    }

    public static void parseAbilities(File file, AddClearService addServ) throws FileNotFoundException {
        addServ.clear("Abilities");
        // skip first two lines
        Scanner fileScan = new Scanner(file);
        fileScan.nextLine();
        fileScan.nextLine();
        // iterate through tokens
        while (fileScan.hasNextLine()) {
            String line = fileScan.nextLine();
            Scanner lineScan = new Scanner(line);
            lineScan.useDelimiter(",");
            int i = 0;
            int abilityID = -1;
            String name = "";
            String type = "";
            int strength = -1;
            String statusEffect = "";
            while (lineScan.hasNext()) {
                String token = lineScan.next();
                switch(i) {
                    case 0:
                        abilityID = Integer.parseInt(token);
                        break;
                    case 1:
                        name = token;
                        break;
                    case 2:
                        type = token;
                        break;
                    case 3:
                        strength = Integer.parseInt(token);
                        break;
                    case 4:
                        if (token.equals("NULL")) {
                            statusEffect = null;
                        } else {
                            statusEffect = token;
                        }
                        addServ.addAbility(abilityID, name, type, strength, statusEffect);
                        i = -1;
                        break;
                }
                i++;
            }
        }
    }

    public static void parseItems(File file, AddClearService addServ) throws FileNotFoundException {
        addServ.clear("Items");
        // skip first two lines
        Scanner fileScan = new Scanner(file);
        fileScan.nextLine();
        fileScan.nextLine();
        // iterate through tokens
        while (fileScan.hasNextLine()) {
            String line = fileScan.nextLine();
            Scanner lineScan = new Scanner(line);
            lineScan.useDelimiter(",");
            int i = 0;
            int itemID = -1;
            String name = "";
            String type = "";
            String description = "";
            Integer power = -1;
            Integer accuracy = -1;
            while (lineScan.hasNext()) {
                String token = lineScan.next();
                switch(i) {
                    case 0:
                        itemID = Integer.parseInt(token);
                        break;
                    case 1:
                        name = token;
                        break;
                    case 2:
                        type = token;
                        break;
                    case 3:
                        description = token;
                        break;
                    case 4:
                        if (token.equals("NULL")) {
                            power = null;
                        } else {
                            power = Integer.parseInt(token);
                        }
                        break;
                    case 5:
                        if (token.equals("NULL")) {
                            accuracy = null;
                        } else {
                            accuracy = Integer.parseInt(token);
                        }
                        addServ.addItem(itemID, name, type, description, power, accuracy);
                        i = -1;
                        break;
                }
                i++;
            }
        }
    }

    public static void parseUsers(File file, AddClearService addServ) throws FileNotFoundException {
        addServ.clear("Users");
        // skip first two lines
        Scanner fileScan = new Scanner(file);
        fileScan.nextLine();
        fileScan.nextLine();
        // iterate through tokens
        while (fileScan.hasNextLine()) {
            String line = fileScan.nextLine();
            Scanner lineScan = new Scanner(line);
            lineScan.useDelimiter(",");
            int i = 0;
            int userID = -1;
            String username = "";
            String password = "";
            while (lineScan.hasNext()) {
                String token = lineScan.next();
                switch(i) {
                    case 0:
                        userID = Integer.parseInt(token);
                        break;
                    case 1:
                        username = token;
                        break;
                    case 2:
                        password = token;
                        addServ.addUser(userID, username, password);
                        i = -1;
                        break;
                }
                i++;
            }
        }
    }

    public static void parseTeams(File file, AddClearService addServ) throws FileNotFoundException {
        addServ.clear("Teams");
        // skip first two lines
        Scanner fileScan = new Scanner(file);
        fileScan.nextLine();
        fileScan.nextLine();
        // iterate through tokens
        while (fileScan.hasNextLine()) {
            String line = fileScan.nextLine();
            Scanner lineScan = new Scanner(line);
            lineScan.useDelimiter(",");
            int i = 0;
            int teamID = -1;
            String name = "";
            int owner = -1;
            while (lineScan.hasNext()) {
                String token = lineScan.next();
                switch(i) {
                    case 0:
                        teamID = Integer.parseInt(token);
                        break;
                    case 1:
                        name = token;
                        break;
                    case 2:
                        owner = Integer.parseInt(token);
                        addServ.addTeam(teamID, name, owner);
                        i = -1;
                        break;
                }
                i++;
            }
        }
    }

    public static void parseCharacters(File file, AddClearService addServ) throws FileNotFoundException {
        addServ.clear("Characters");
        // skip first two lines
        Scanner fileScan = new Scanner(file);
        fileScan.nextLine();
        fileScan.nextLine();
        // iterate through tokens
        while (fileScan.hasNextLine()) {
            String line = fileScan.nextLine();
            Scanner lineScan = new Scanner(line);
            lineScan.useDelimiter(",");
            int i = 0;
            int characterID = -1;
            String name = "";
            String className = "";
            int teamID = -1;
            String description = "";
            int health = -1;
            int powerLevel = -1;
            while (lineScan.hasNext()) {
                String token = lineScan.next();
                switch(i) {
                    case 0:
                        characterID = Integer.parseInt(token);
                        break;
                    case 1:
                        name = token;
                        break;
                    case 2:
                        className = token;
                        break;
                    case 3:
                        teamID = Integer.parseInt(token);
                        break;
                    case 4:
                        description = token;
                        break;
                    case 5:
                        health = Integer.parseInt(token);
                        break;
                    case 6:
                        powerLevel = Integer.parseInt(token);
                        addServ.addCharacter(characterID, name, className, teamID, description, health, powerLevel);
                        i = -1;
                        break;
                }
                i++;
            }
        }
    }

    public static void parseBattles(File file, AddClearService addServ) throws FileNotFoundException {
        addServ.clear("Battles");
        // skip first two lines
        Scanner fileScan = new Scanner(file);
        fileScan.nextLine();
        fileScan.nextLine();
        // iterate through tokens
        while (fileScan.hasNextLine()) {
            String line = fileScan.nextLine();
            Scanner lineScan = new Scanner(line);
            lineScan.useDelimiter(",");
            int i = 0;
            int battleID = -1;
            int winnerID = -1;
            int loserID = -1;
            while (lineScan.hasNext()) {
                String token = lineScan.next();
                switch(i) {
                    case 0:
                        battleID = Integer.parseInt(token);
                        break;
                    case 1:
                        winnerID = Integer.parseInt(token);
                        break;
                    case 2:
                        loserID = Integer.parseInt(token);
                        addServ.addBattle(battleID, winnerID, loserID);
                        i = -1;
                        break;
                }
                i++;
            }
        }
    }

    public static void parseHasItem(File file, AddClearService addServ) throws FileNotFoundException {
        addServ.clear("HasItem");
        // skip first two lines
        Scanner fileScan = new Scanner(file);
        fileScan.nextLine();
        fileScan.nextLine();
        // iterate through tokens
        while (fileScan.hasNextLine()) {
            String line = fileScan.nextLine();
            Scanner lineScan = new Scanner(line);
            lineScan.useDelimiter(",");
            int i = 0;
            int characterID = -1;
            int itemID = -1;
            int charges = -1;
            while (lineScan.hasNext()) {
                String token = lineScan.next();
                switch(i) {
                    case 0:
                        characterID = Integer.parseInt(token);
                        break;
                    case 1:
                        itemID = Integer.parseInt(token);
                        break;
                    case 2:
                        charges = Integer.parseInt(token);
                        addServ.addHasItem(characterID, itemID, charges);
                        i = -1;
                        break;
                }
                i++;
            }
        }
    }

    public static void parseKnowsAbility(File file, AddClearService addServ) throws FileNotFoundException {
        addServ.clear("KnowsAbility");
        // skip first two lines
        Scanner fileScan = new Scanner(file);
        fileScan.nextLine();
        fileScan.nextLine();
        // iterate through tokens
        while (fileScan.hasNextLine()) {
            String line = fileScan.nextLine();
            Scanner lineScan = new Scanner(line);
            lineScan.useDelimiter(",");
            int i = 0;
            int characterID = -1;
            int abilityID = -1;
            while (lineScan.hasNext()) {
                String token = lineScan.next();
                switch(i) {
                    case 0:
                        characterID = Integer.parseInt(token);
                        break;
                    case 1:
                        abilityID = Integer.parseInt(token);
                        addServ.addKnowsAbility(characterID, abilityID);
                        i = -1;
                        break;
                }
                i++;
            }
        }
    }

    public static void parseFoughtBy(File file, AddClearService addServ) throws FileNotFoundException {
        addServ.clear("FoughtBy");
        // skip first two lines
        Scanner fileScan = new Scanner(file);
        fileScan.nextLine();
        fileScan.nextLine();
        // iterate through tokens
        while (fileScan.hasNextLine()) {
            String line = fileScan.nextLine();
            Scanner lineScan = new Scanner(line);
            lineScan.useDelimiter(",");
            int i = 0;
            int teamID = -1;
            int battleID = -1;
            while (lineScan.hasNext()) {
                String token = lineScan.next();
                switch(i) {
                    case 0:
                        teamID = Integer.parseInt(token);
                        break;
                    case 1:
                        battleID = Integer.parseInt(token);
                        addServ.addFoughtBy(teamID, battleID);
                        i = -1;
                        break;
                }
                i++;
            }
        }
    }

}
