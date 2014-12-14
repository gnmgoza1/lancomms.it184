/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package lancomms;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.sun.security.auth.module.NTSystem;
import javax.swing.JFrame;

/**
 *
 * @author user
 */
public class WebCamFeed {
    
    public void getFeed(){
        NTSystem NTSystem = new NTSystem();//test
        System.out.println(NTSystem.getName());//test
        Webcam webcam = Webcam.getDefault();
        WebcamPanel panel = new WebcamPanel(webcam);

        panel.setFPSDisplayed(true);
        panel.setDisplayDebugInfo(true);
        panel.setImageSizeDisplayed(true);
//        panel.setMirrored(true);

        JFrame window = new JFrame("Test webcam panel");
        window.add(panel);
        window.setResizable(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.pack();
        window.setVisible(true);
    }
}