/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coderedrobotics.tiberius.libs.dash;

/**
 *
 * @author laptop
 */
public class DashBoard {

    DataStreamingModule dataStreamingModule;
    Connection connection;
    CommunicationsThread communicationsThread;
    SynchronizedRegisterArray synchronizedRegisterArray;
    DashBoard dashBoard;

    public DashBoard() {
        dataStreamingModule = new DataStreamingModule();
        connection = new Connection();
        synchronizedRegisterArray = new SynchronizedRegisterArray();
        communicationsThread = new CommunicationsThread(
                connection,
                synchronizedRegisterArray,
                dataStreamingModule);
    }

    public void streamPacket(double val, String name) {
        dataStreamingModule.sendPacket(val, name);
    }

    public double getRegister(String name) {
        return synchronizedRegisterArray.get(name);
    }

    public void setRegister(String name, double val) {
        synchronizedRegisterArray.setRegister(name, val);
    }

    public synchronized void prtln(String ln, int channel) {
        if (channel == synchronizedRegisterArray.get("DEBUGCHANNEL")) {
            for (int i = 0; i < ln.length(); i++) {
                streamPacket(ln.charAt(i), "DEBUG");
            }
            streamPacket('\n', "DEBUG");
        }
        if (0 == synchronizedRegisterArray.get("DEBUGCHANNEL")) {
            for (int i = 0; i < ln.length(); i++) {
                streamPacket(ln.charAt(i), "DRIVERCONSOLE");
            }
            streamPacket('\n', "DRIVERCONSOLE");
        }
    }
}