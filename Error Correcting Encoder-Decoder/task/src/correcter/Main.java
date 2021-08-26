package correcter;

import correcter.correction.Decode;
import correcter.correction.Encode;
import correcter.correction.Send;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        String command = "";
        Scanner scanner = new Scanner(System.in);

        while (!command.matches("(encode|send|decode)")) {
            System.out.print("Write a mode: ");
            command = scanner.nextLine();
        }

        switch (command) {
            case "encode":
                Encode.perform();
                break;
            case "send":
                Send.perform();
                break;
            case "decode":
                Decode.perform();
                break;
        }

   }
}
