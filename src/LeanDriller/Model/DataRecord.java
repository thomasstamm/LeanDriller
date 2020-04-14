/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LeanDriller.Model;

import java.util.ArrayList;

/**
 *
 * @author Thomas Stamm
 */
public class DataRecord {

    private final String recordType;
    private final String recordIdentifier;
    private final String recordData;

    public DataRecord(String recordType, String recordIdentifier, 
            String recordData) {
        
        this.recordType = recordType;
        this.recordIdentifier = recordIdentifier;
        this.recordData = recordData;
    }

    public String getRecordType() {
        return recordType;
    }

    public String getRecordIdentifier() {
        return recordIdentifier;
    }
    
    public String getRecordData() {
        return recordData;
    }
    
    public static void listDataStream(ArrayList<DataRecord> dataStream) {
        for (DataRecord dataRecord : dataStream) {
            System.out.println("Datarecord Tpe: " + dataRecord.recordType 
                    + " Datarecord Identifier: " + dataRecord.recordIdentifier
                    + " Datarecord Data: " + dataRecord.recordData);
        }
    }

}
