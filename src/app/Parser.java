package app;

import services.AbilityService;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Parser {

    public static void parseAbilities(File file, AbilityService abilServ) throws FileNotFoundException {
        abilServ.clearAbilities();
        Scanner fileScan = new Scanner(file);
        fileScan.nextLine();
        fileScan.nextLine();
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
                System.out.println(token + " with i = " + i);
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
                        statusEffect = token;
                        abilServ.addAbility(abilityID, name, type, strength, statusEffect);
                        i = -1;
                        break;
                }
                i++;
            }
        }
    }
}
