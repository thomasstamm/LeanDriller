/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LeanDriller.Model;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UTFDataFormatException;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Thomas
 */
public class IOManagerExtended {

    /**
     * This class supports reading and writing per cluster of characters (token)
     * for 2 encoding methods: MODE = "UTF16" and MODE = "Modified UTF8". Java
     * uses the "UTF16" mode.
     *
     * The default value is set to "UTF16". The value can be changed via the
     * MODE butten on the main screen.
     *
     * Remark (1): a file created in UTF16 must be read in UTF16 mode and
     * likewise a file created in modified UTF8 mode must be read in modified
     * UTF8 mode.
     *
     * Remark (2): In the modified UTF8 mode the writeUTF and readUTF methods
     * are used. These methods work on a per token bases. In case of UTF16 mode,
     * the readChar and writeChars methods are used. The writeChars writes per
     * token. After each token a separator (codepoint with unicode 0002) is
     * written to the outputstream. The readChar reads per character until a
     * separator is encountered.
     */
    public static final char TOKENSEPARATOR = '\u0002'; //misuse of STX Start of Text
    public static final char SUBTOKENSEPARATOR = '\u0003'; //misuse ETX End of Text
    private static final String MODE = "UTF16";

    public static boolean SaveFile(String AttachedFile, ArrayList<DataRecord> dataStream) {

        DataOutputStream out = null;
        ListIterator<DataRecord> iterator = dataStream.listIterator();

        try {
            out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(AttachedFile)));

            while (iterator.hasNext()) {

                DataRecord dr = iterator.next();

                String recordType = dr.getRecordType();
                writeToken(out, recordType);
                String recordIdentifier = dr.getRecordIdentifier();
                writeToken(out, recordIdentifier);
                String recordData = dr.getRecordData();
                writeToken(out, recordData);

            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(IOManagerExtended.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | NullPointerException ex) {
            Logger.getLogger(IOManagerExtended.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(IOManagerExtended.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return true;
    }

    public static ArrayList<DataRecord> LoadFile(String AttachedFile) {

        ArrayList<DataRecord> dataStream = new ArrayList<>();

        DataInputStream in = null;

        try {

            in = new DataInputStream(new BufferedInputStream(new FileInputStream(AttachedFile)));

            while (true) {

                String recordType = readToken(in);
                String recordIdentifier = readToken(in);
                String recordData = readToken(in);

                DataRecord dr = new DataRecord(recordType, recordIdentifier,
                        recordData);

                dataStream.add(dr);

            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(IOManagerExtended.class.getName()).log(Level.SEVERE, null, ex);
        } catch (EOFException ex) { // EOFException is misused to exit the while loop

        } catch (UTFDataFormatException ex) {
            System.out.println("UTFDataFormatException");
        } catch (IOException | NullPointerException ex) {
            Logger.getLogger(IOManagerExtended.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (in != null) {  // evading derefencing possible null pointer
                    in.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(IOManagerExtended.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return dataStream;
    }

    private static String readToken(DataInputStream in) throws
            IOException, EOFException, UTFDataFormatException {
        switch (MODE) {
            case "Modified UTF8":
                return in.readUTF();
            case "UTF16":
                StringBuilder sb = new StringBuilder();
                while (true) {
                    char c = in.readChar();
                    if (c != TOKENSEPARATOR) { // \u0002 code point used as separator
                        sb.append(c);
                    } else {
                        return sb.toString();
                    }
                }
            default:
                return null;
        }
    }

    private static void writeToken(DataOutputStream out, String token)
            throws IOException {
        switch (MODE) {
            case "Modified UTF8":
                out.writeUTF(token);
                break;
            case "UTF16":
                out.writeChars(token);
                out.writeChar(TOKENSEPARATOR); //Remark: writeChar takes an integer as input
                break;
        }

    }

}
