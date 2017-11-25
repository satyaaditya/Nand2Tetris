package com.company;
import java.io.*;
import java.util.Scanner;

public class Parser {
    static int A_COMMAND = 0;
    static int C_COMMAND = 1;
    static int L_COMMAND = 2;

    private String current_command;
    private Scanner input;

    public Parser(String filename) {
        File f = new File(filename);
        try {
            input = new Scanner(f);
        } catch (FileNotFoundException e) {
            System.out.println("file not found");
        }
    }

    public boolean hasMoreCommands() {
        if(input.hasNextLine())
            return true;
        else{
            input.close();
            return false;
        }
    }

    public void readNextLine() {
        String currentLine;
        do {
            currentLine = input.nextLine().trim();
        } while (currentLine.equals("") || currentLine.substring(0, 2).equals("//"));
        String[] command = currentLine.split("//");
        current_command = command[0];
    }

    public int getCommandType() {
        char ch = current_command.charAt(0);
        if (ch == '@')
            return A_COMMAND;
        if (ch == '(')
            return L_COMMAND;
        return C_COMMAND;
    }

    public String getSymbol() {
        String symbol = "";
        int com_type = this.getCommandType();
        if (com_type == A_COMMAND) {
            symbol = current_command.substring(1, current_command.length());
        } else if (com_type == L_COMMAND) {
            symbol = current_command.substring(1, current_command.length() - 1);
        }
        return symbol;
    }

    public String get_dest() {
        String dest = null;
        if(this.getCommandType() == C_COMMAND && current_command.indexOf("=")!= -1)
             dest = current_command.split("=")[0];
            return dest;
    }

    public String get_comp(){
        String comp = null;

        if (this.getCommandType() == C_COMMAND) {
            if (current_command.indexOf("=") != -1) {
                comp = current_command.split("=")[1].trim();
            } else if (current_command.indexOf(";") != -1) {
                comp = current_command.split(";")[0].trim();
            } else { }
        }
        return comp;
    }

    public String get_jump() {
        String jump = null;
        if (this.getCommandType() == C_COMMAND) {
            if (current_command.indexOf(";") != -1) {
                jump = current_command.split(";")[1].trim();
            }
        }
        return jump;
    }

}
