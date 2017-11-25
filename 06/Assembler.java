package com.company;
import java.util.*;
import java.io.File;
import java.io.PrintStream;

public class Assembler {
    static int A_COMMAND = 0;
    static int C_COMMAND = 1;
    static int L_COMMAND = 2;
    static int VAR = 16;

    public static void iteration1(Parser parser,SymbolTable symbol_table){
        int count = -1;
        String symbol = null;
        while(parser.hasMoreCommands()){
            parser.readNextLine();
            if(parser.getCommandType() == L_COMMAND){
                symbol = parser.getSymbol();
                symbol_table.addEntry(symbol,count+1);
            }
            else
                count++;
        }
    }

    public static void iteration2(Parser parser,CodeGeneration code_generator,SymbolTable symbol_table){
        while(parser.hasMoreCommands()){
            parser.readNextLine();
            int com_type = parser.getCommandType();

            if(com_type == A_COMMAND){
                String symbol = parser.getSymbol();
                String command = generate_A_Command(symbol,symbol_table);
                System.out.println(command);
            }
            else if(com_type == C_COMMAND){
                String comp = parser.get_comp();
                String dest = parser.get_dest();
                String jump = parser.get_jump();
                String command = generate_C_Command(code_generator,dest,comp,jump);
                System.out.println(command);
            }
        }
    }

    private static boolean check_numeric(String symbol){
        if(symbol.length()==0)
            return false;
        else{
            for(char ch : symbol.toCharArray()){
                if(!Character.isDigit(ch))
                    return false;
            }
            return true;
        }
    }

    public static String convert_to_16bit(String binary){
        int zeroes = 16 - binary.length();
        StringBuilder sb = new StringBuilder();
        for(int i = zeroes; i > 0; i--){
            sb.append("0");
        }
        sb.append(binary);
        return sb.toString();
    }

    public  static String generate_A_Command(String symbol,SymbolTable sym_tab){
        int address;
        String command = null;
        if(check_numeric(symbol))
            address = Integer.parseInt(symbol);
        else{
            if(sym_tab.contains(symbol))
                address = sym_tab.getAddress(symbol);
            else{
                address = VAR++;
                sym_tab.addEntry(symbol,address);
            }
        }
        String binary  = Integer.toBinaryString(address);
        command = convert_to_16bit(binary);
        return command;
    }

    public static String generate_C_Command(CodeGeneration codegenerator,String dest,String comp,String jump){
        StringBuilder sb = new StringBuilder();
        String dest_code = codegenerator.dest(dest);
        String comp_code = codegenerator.comp(comp);
        String jump_code = codegenerator.jump(jump);
        sb.append("111");
        sb.append(comp_code);
        sb.append(dest_code);
        sb.append(jump_code);
        return sb.toString();
    }

    public static void main(String args[]){
        StringBuilder sb = new StringBuilder();
        Scanner sc = new Scanner(System.in);
        String filee = sc.next();
        sb.append("C:/Users/sandeep/IdeaProjects/assembler/src/com/company/");
        sb.append(filee);
        String filename = sb.toString();
        Parser parser = new Parser(filename);
        CodeGeneration codegenerator = new CodeGeneration();
        SymbolTable symbol_table = new SymbolTable();

        iteration1(parser,symbol_table);

        parser = new Parser(filename);
        String output_filename = filename.split("[.]")[0] + "_YX.hack";

        try{
            System.setOut(new PrintStream(new File(output_filename)));
        }
        catch(Exception e){
            System.out.println("exception occured while copying to output file");
        }
        iteration2(parser,codegenerator,symbol_table);
    }
}
