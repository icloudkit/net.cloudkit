package net.cloudkit.enterprises.jna;

import com.sun.jna.platform.win32.WinDef;

import java.util.concurrent.TimeUnit;

public class Test {


    public static void main(String args[]) {
        String winClassName = "TFrmHwnd";
        String winCaption = "窗体句柄";
        WinDef.HWND WinHwnd = Win32Util.findHandleByClassName(winClassName, 10, TimeUnit.SECONDS, winCaption);

//		int[][]  keyCombination = new int[1][1] ;
//		keyCombination[0][0] = 0x0D;
//		Win32Util.simulateKeyboardEvent(loginHwnd, keyCombination);


        if (WinHwnd == null) {
            System.out.println("*** not find window " + winClassName + " caption:" + winCaption + "***");
            return;
        }

        /*
        HWND snEdit = Win32Util.findHandleByClassName(loginHwnd, "Edit", 10, TimeUnit.SECONDS, 1);
        if (snEdit != null) {
            //Win32Util.simulateCharInput(snEdit, "WWWWWW");
        }

        HWND hostEdit = Win32Util.findHandleByClassName(loginHwnd, "Edit", 10, TimeUnit.SECONDS, 2);
        if (hostEdit != null) {
            //Win32Util.simulateCharInput(hostEdit, "2.2.2.2");
        }

        HWND okButton = Win32Util.findHandleByClassName(loginHwnd, "Button", 10, TimeUnit.SECONDS, "OK");
        if (okButton != null) {
            //Win32Util.simulateClick(okButton);
        }
        */
    }


    private static WinDef.HWND findInputHandle(String winHandleclassName, String inputHandleClassName, int no) {

        WinDef.HWND hwnd = Win32Util.findHandleByClassName(winHandleclassName, 10, TimeUnit.SECONDS, "New Session");
        if (hwnd == null) {
            System.out.println("*** not find window " + winHandleclassName + " ***");
            return null;
        }
        return Win32Util.findHandleByClassName(hwnd, inputHandleClassName, 10, TimeUnit.SECONDS, no);
    }

}
