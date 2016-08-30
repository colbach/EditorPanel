package photopanel.views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import javax.swing.*;
import photopanel.StaticManager;
import photopanel.boxes.Boxes;

/**
 * EDITORPANEL - Tool- und Pluginprogrammierung 2016 - Hochschule Trier
 * @author Christian Colbach
 */
public class MainFrame extends javax.swing.JFrame {

    int offSetNewBoxX = 10, offSetNewBoxY = 10;
    
    public MainFrame() {
        initComponents();
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                photoPanelKeyTyped(evt);
            }
        });
        
        for(String category : Boxes.getCategorys()) {
            JMenu categoryMenu = new JMenu(category);
            ArrayList<JMenuItem> tmp = new ArrayList<>();
            String previousName = "";
            for(Class box : Boxes.getBoxesForCategory(category)) {
                String name = Boxes.getNameForBox(box);
                JMenuItem boxItem = new JMenuItem(name);
                if(previousName.compareToIgnoreCase(name) > 0) {
                    tmp.add(tmp.size()-1, boxItem);
                } else {
                    tmp.add(0, boxItem);
                }
                previousName = name;
                boxItem.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        offSetNewBoxX += 10;
                        offSetNewBoxY += 10;
                        if(offSetNewBoxX > 100)
                            offSetNewBoxX = 10;
                        if(offSetNewBoxY > 100)
                            offSetNewBoxY = 10;
                        StaticManager.addBox(Boxes.newInstance(box, offSetNewBoxX, offSetNewBoxY));
                        photoPanel.repaint();
                    }
                });
                for(JMenuItem mi : tmp) {
                    categoryMenu.add(mi);
                }
            }
            boxMenu.add(categoryMenu);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenu3 = new javax.swing.JMenu();
        jScrollPane1 = new javax.swing.JScrollPane();
        photoPanel = new photopanel.views.OverviewPanel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        speichernMenuItem = new javax.swing.JMenuItem();
        speichernUnterMenuItem = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        boxMenu = new javax.swing.JMenu();

        jMenu3.setText("jMenu3");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jScrollPane1.setFocusable(false);

        photoPanel.setBackground(new java.awt.Color(255, 255, 255));
        photoPanel.setPreferredSize(new java.awt.Dimension(5000, 500));
        photoPanel.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                photoPanelMouseDragged(evt);
            }
        });
        photoPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                photoPanelMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                photoPanelMouseReleased(evt);
            }
        });
        photoPanel.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                photoPanelKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout photoPanelLayout = new javax.swing.GroupLayout(photoPanel);
        photoPanel.setLayout(photoPanelLayout);
        photoPanelLayout.setHorizontalGroup(
            photoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 2000, Short.MAX_VALUE)
        );
        photoPanelLayout.setVerticalGroup(
            photoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 2000, Short.MAX_VALUE)
        );

        jScrollPane1.setViewportView(photoPanel);

        jMenu2.setText("Projekt");

        jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem2.setText("Ausführen");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem2);

        jMenuItem1.setText("Zurücksetzen");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem1);

        speichernMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        speichernMenuItem.setText("Speichern");
        speichernMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                speichernMenuItemActionPerformed(evt);
            }
        });
        jMenu2.add(speichernMenuItem);

        speichernUnterMenuItem.setText("Speichern unter");
        speichernUnterMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                speichernUnterMenuItemActionPerformed(evt);
            }
        });
        jMenu2.add(speichernUnterMenuItem);

        jMenuItem4.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem4.setText("Öffnen");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem4);

        jMenuBar1.add(jMenu2);

        boxMenu.setText("Kisten");
        jMenuBar1.add(boxMenu);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 981, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 612, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void photoPanelMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_photoPanelMouseDragged
        ((OverviewPanel)photoPanel).mouseIsDragging(pressedX, pressedY, evt.getX(), evt.getY());
    }//GEN-LAST:event_photoPanelMouseDragged

    private void photoPanelKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_photoPanelKeyTyped
        ((OverviewPanel)photoPanel).keyTyped(evt.getKeyChar());
    }//GEN-LAST:event_photoPanelKeyTyped

    private int pressedX = -1;
    private int pressedY = -1;
    
    private void photoPanelMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_photoPanelMousePressed
        ((OverviewPanel)photoPanel).mousePressed(evt.getX(), evt.getY());
        pressedX = evt.getX();
        pressedY = evt.getY();
    }//GEN-LAST:event_photoPanelMousePressed

    private void photoPanelMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_photoPanelMouseReleased
        int releasedX = evt.getX();
        int releasedY = evt.getY();
        if(pressedX != releasedX && pressedY != releasedY) {
            ((OverviewPanel)photoPanel).mouseDragged(pressedX, pressedY, releasedX, releasedY);
        }
        pressedX = -1;
        pressedY = -1;
        ((OverviewPanel)photoPanel).mouseReleased(evt.getX(), evt.getY());
    }//GEN-LAST:event_photoPanelMouseReleased

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        StaticManager.reset();
        offSetNewBoxX = 10;
        offSetNewBoxY = 10;
        photoPanel.repaint();
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void speichernMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_speichernMenuItemActionPerformed
        boolean saved = StaticManager.save();
        if(!saved) {
            speichernUnterMenuItemActionPerformed(null);
        }
    }//GEN-LAST:event_speichernMenuItemActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        JFileChooser chooser = new JFileChooser(".");
        chooser.setFileFilter(new ObjFileFilter());
        chooser.setAcceptAllFileFilterUsed(false);
        int rueckgabeWert = chooser.showOpenDialog(this);
        if(rueckgabeWert == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            if(file.exists()) {
                StaticManager.loadFromFile(file);
                photoPanel.repaint();
            } else {
                JOptionPane.showMessageDialog(
                    null,
                    "Datei existiert nicht!",
                    "Fehler",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void speichernUnterMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_speichernUnterMenuItemActionPerformed
        JFileChooser chooser = new JFileChooser(".");
        chooser.setFileFilter(new ObjFileFilter());
        chooser.setAcceptAllFileFilterUsed(false);
        int rueckgabeWert = chooser.showSaveDialog(this);
        if(rueckgabeWert == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            if(!file.getAbsolutePath().toLowerCase().endsWith(".obj")) {
                file = new File(file.getAbsolutePath() + ".obj");
            }
            if(file.exists()) {
                Object[] options = {"Ja", "Nein"};
                int n = JOptionPane.showOptionDialog(
                            this,
                            "Die Datei " + file.getName() + " existiert bereits. Wollen sie diese Datei überschreiben?",
                            "Datei überschreiben",
                            JOptionPane.YES_NO_CANCEL_OPTION,
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            options,
                            options[0]);
                if(n == 0) {
                    System.out.println("Ueberschreiben...");
                    StaticManager.saveToFile(file);
                    photoPanel.repaint();
                } else {
                    System.out.println("Abbruch");
                }
            } else {
                System.out.println("Speichern...");
                StaticManager.saveToFile(file);
                photoPanel.repaint();
            }
        }
        
    }//GEN-LAST:event_speichernUnterMenuItemActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        
        if(StaticManager.canRun()) {
            System.out.println("ausführen...");
            StaticManager.run();
        } else {
            JOptionPane.showMessageDialog(null,
                "Kann nicht ausgeführt werden! (" + StaticManager.errorMessage + ")",
                "Fehler",
                JOptionPane.ERROR_MESSAGE
            );
        }
        
        
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        try {
            // Set System L&F
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (UnsupportedLookAndFeelException e) {
            // handle exception
        } catch (ClassNotFoundException e) {
            // handle exception
        } catch (InstantiationException e) {
            // handle exception
        } catch (IllegalAccessException e) {
            // handle exception
        }

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu boxMenu;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel photoPanel;
    private javax.swing.JMenuItem speichernMenuItem;
    private javax.swing.JMenuItem speichernUnterMenuItem;
    // End of variables declaration//GEN-END:variables
}
