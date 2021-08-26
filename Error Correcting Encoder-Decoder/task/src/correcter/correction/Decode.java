package correcter.correction;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Locale;

public class Decode {

    public static void perform() {

        /* Getting data to decode from file received.txt */

        String encoded = "";
        StringBuilder encodedBuilder = new StringBuilder();

        try {
            FileInputStream fileInputStream = new FileInputStream("received.txt");

            byte[] buffer = new byte[fileInputStream.available()];

            fileInputStream.read(buffer, 0, buffer.length);

            for (byte b : buffer) {
                String binary = Integer.toBinaryString(Byte.toUnsignedInt(b));

                while (binary.length() < 8) {
                    binary = "0" + binary;
                }

                encodedBuilder.append(binary).append(" ");
            }

            encoded = encodedBuilder.toString().trim();

        } catch (IOException e) {
            System.out.println("ERROR: something bad happened during opening file received.txt");
        }

        StringBuilder hexView = new StringBuilder();

        for (String str : encoded.trim().split(" ")) {
            String toAppend = Integer.toHexString(Integer.parseInt(str, 2)).toUpperCase(Locale.ROOT);

            if (toAppend.length() < 2) {
                toAppend = "0" + toAppend;
            }

            hexView.append(toAppend).append(" ");
        }

        System.out.println("\nreceived.txt:");
        System.out.println("hex view: " + hexView);
        System.out.println("bin view: " + encoded);



        /* Processing and writing data to decoded.txt */

        StringBuilder correct = new StringBuilder();

        for (String str : encoded.trim().split(" ")) {

            String firstBit = String.valueOf(str.charAt(0));
            String secondBit = String.valueOf(str.charAt(1));
            String thirdBit = String.valueOf(str.charAt(2));
            String fourthBit = String.valueOf(str.charAt(3));
            String fifthBit = String.valueOf(str.charAt(4));
            String sixthBit = String.valueOf(str.charAt(5));
            String seventhBit = String.valueOf(str.charAt(6));
            String eighthBit = String.valueOf(str.charAt(7));

            boolean firstBitIsCorrect = false;
            boolean secondBitIsCorrect = false;
            boolean fourthBitIsCorrect = false;

            if ((Integer.parseInt(thirdBit)
                    + Integer.parseInt(fifthBit)
                    + Integer.parseInt(seventhBit)) % 2 == Integer.parseInt(firstBit)) {

                firstBitIsCorrect = true;

            }

            if ((Integer.parseInt(thirdBit)
                    + Integer.parseInt(sixthBit)
                    + Integer.parseInt(seventhBit)) % 2 == Integer.parseInt(secondBit)) {

                secondBitIsCorrect = true;

            }

            if ((Integer.parseInt(fifthBit)
                    + Integer.parseInt(sixthBit)
                    + Integer.parseInt(seventhBit)) % 2 == Integer.parseInt(fourthBit)) {

                fourthBitIsCorrect = true;

            }

            if (firstBitIsCorrect && secondBitIsCorrect && fourthBitIsCorrect) {

                correct
                        .append(firstBit)
                        .append(secondBit)
                        .append(thirdBit)
                        .append(fourthBit)
                        .append(fifthBit)
                        .append(sixthBit)
                        .append(seventhBit)
                        .append(eighthBit)
                        .append(" ");

            } else if (!firstBitIsCorrect && !secondBitIsCorrect && fourthBitIsCorrect) {

                if (thirdBit.equals("0")) {
                    thirdBit = "1";
                } else if (thirdBit.equals("1")) {
                    thirdBit = "0";
                }

                correct
                        .append(firstBit)
                        .append(secondBit)
                        .append(thirdBit)
                        .append(fourthBit)
                        .append(fifthBit)
                        .append(sixthBit)
                        .append(seventhBit)
                        .append(eighthBit)
                        .append(" ");


            } else if (!firstBitIsCorrect && secondBitIsCorrect && !fourthBitIsCorrect) {

                if (fifthBit.equals("0")) {
                    fifthBit = "1";
                } else if (fifthBit.equals("1")) {
                    fifthBit = "0";
                }

                correct
                        .append(firstBit)
                        .append(secondBit)
                        .append(thirdBit)
                        .append(fourthBit)
                        .append(fifthBit)
                        .append(sixthBit)
                        .append(seventhBit)
                        .append(eighthBit)
                        .append(" ");


            } else if (firstBitIsCorrect && !secondBitIsCorrect && !fourthBitIsCorrect) {

                if (sixthBit.equals("0")) {
                    sixthBit = "1";
                } else if (sixthBit.equals("1")) {
                    sixthBit = "0";
                }

                correct
                        .append(firstBit)
                        .append(secondBit)
                        .append(thirdBit)
                        .append(fourthBit)
                        .append(fifthBit)
                        .append(sixthBit)
                        .append(seventhBit)
                        .append(eighthBit)
                        .append(" ");


            } else if (!firstBitIsCorrect && !secondBitIsCorrect && !fourthBitIsCorrect) {

                if (seventhBit.equals("0")) {
                    seventhBit = "1";
                } else if (seventhBit.equals("1")) {
                    seventhBit = "0";
                }

                correct
                        .append(firstBit)
                        .append(secondBit)
                        .append(thirdBit)
                        .append(fourthBit)
                        .append(fifthBit)
                        .append(sixthBit)
                        .append(seventhBit)
                        .append(eighthBit)
                        .append(" ");

            }
        }


        StringBuilder decoded = new StringBuilder();

        int counter = 0;

        for (String str : correct.toString().trim().split( " ")) {

            decoded.append(str.charAt(2)).append(str.charAt(4)).append(str.charAt(5)).append(str.charAt(6));

            if (counter == 1) {
                decoded.append(" ");
                counter = 0;
                continue;
            }

            counter++;
        }

        StringBuilder removed = new StringBuilder();
        StringBuilder decodedHexView = new StringBuilder();
        StringBuilder decodedText = new StringBuilder();

        for (String str : decoded.toString().trim().split(" ")) {

            int value = Integer.parseInt(str, 2);

            if (value == 0) {
                continue;
            }

            removed.append(str).append(" ");

            String hexValue = Integer.toString(value, 16);
            if (hexValue.length() < 2) hexValue = "0" + hexValue;
            decodedHexView.append(hexValue).append(" ");

            decodedText.append((char) value);

        }

        System.out.println("\ndecoded.txt:");
        System.out.println("correct: " + correct);
        System.out.println("decode: " + decoded);
        System.out.println("remove: " + removed);
        System.out.println("hex view: " + decodedHexView);
        System.out.println("text view: " + decodedText);

        try {
            FileWriter fileWriter = new FileWriter("decoded.txt");
            fileWriter.write(decodedText.toString());
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("ERROR: something bad happened during writing data to file decoded.txt");
        }

    }

}

