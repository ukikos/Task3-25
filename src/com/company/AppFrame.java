package com.company;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AppFrame extends JFrame {
    private JPanel mainPanel;
    private JTextArea inputList;
    private JTextArea outputList;
    private JButton loadButton;
    private JButton processButton;
    private JButton saveButton;
    private JLabel inputLabel;
    private JLabel outputLabel;
    private JTabbedPane tabbedPane;
    private JPanel MyQueuePanel;
    private JPanel JavaQueuePanel;
    private JTextArea inputQueue2;
    private JTextArea outputQueue2;
    private JLabel inputLabel2;
    private JLabel outputLabel2;
    private JButton processbutton2;
    private JButton savebutton2;
    private JButton loadButton2;
    private Border compound, raisedbevel, loweredbevel;
    private JFileChooser openJFileChooser;
    private JFileChooser saveJFileChooser;

    public AppFrame(String title) {
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.pack();

        raisedbevel = BorderFactory.createRaisedBevelBorder();
        loweredbevel = BorderFactory.createLoweredBevelBorder();
        compound = BorderFactory.createCompoundBorder(raisedbevel, loweredbevel);
        inputList.setBorder(compound);
        outputList.setBorder(compound);
        inputQueue2.setBorder(compound);
        outputQueue2.setBorder(compound);

        openJFileChooser = new JFileChooser();
        saveJFileChooser = new JFileChooser();

        openJFileChooser.setCurrentDirectory(new File("./examples"));
        saveJFileChooser.setCurrentDirectory(new File("./examples"));

        FileFilter txtFilter = new FileNameExtensionFilter("Text files (*.txt)", "txt");

        openJFileChooser.addChoosableFileFilter(txtFilter);
        saveJFileChooser.addChoosableFileFilter(txtFilter);

        saveJFileChooser.setAcceptAllFileFilterUsed(false);
        saveJFileChooser.setDialogType(JFileChooser.SAVE_DIALOG);
        saveJFileChooser.setApproveButtonText("Save");

        processButton.addActionListener(new ActionListener() {         //кнопка "выполнить" во вкладке MyQueue дублирует элементы очереди
            @Override
            public void actionPerformed(ActionEvent e) {
                String str = inputList.getText();                      //принимаем текст из поля ввода

                String[] queueItems = str.split("[\\s$]+");      //пробел будет считаться разделителем элементов
                MyQueue<String> queue = new MyQueue<>();

                for(String item : queueItems) {
                    queue.add(item);                                   //добавляем элементы в очередь
                }

                try {
                    queue.duplicateElements();                         //дублируем элементы очереди
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                StringBuilder strBuilder = new StringBuilder();        //составляем ответ для вывода
                while(queue.getSize() > 0) {
                    try {
                        strBuilder.append(queue.get());
                        if(queue.getSize() > 0) {
                            strBuilder.append(" ");
                        }
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }
                String result = strBuilder.toString();

                outputList.setText(result);                               //выводим преобразованный лист в видео строки
            }
        });

        loadButton.addActionListener(new ActionListener() {               //открытие файлов во вкладке MyQueue
            @Override
            public void actionPerformed(ActionEvent e) {
                if (openJFileChooser.showOpenDialog(AppFrame.this) == JFileChooser.APPROVE_OPTION) {
                    try (Scanner scan = new Scanner(openJFileChooser.getSelectedFile())) {
                        scan.useDelimiter("\\Z");
                        inputList.setText(scan.next());
                    } catch (Exception ex) {
                        outputList.setText("Error.(Чтобы считывался русский текст, нужно его сохранить в кодировке utf-8)");
                    }
                }
            }
        });

        saveButton.addActionListener(new ActionListener() {                //сохранение файлов во вкладке MyQueue
            @Override
            public void actionPerformed(ActionEvent e) {
                if(saveJFileChooser.showSaveDialog(AppFrame.this) == JFileChooser.APPROVE_OPTION) {
                    String filename = saveJFileChooser.getSelectedFile().getPath();
                    if (!filename.toLowerCase().endsWith(".txt")) {
                        filename += ".txt";
                    }
                    try (FileWriter wr = new FileWriter(filename)) {
                        wr.write(outputList.getText());
                        JOptionPane.showMessageDialog(AppFrame.this,
                                "Файл '" + saveJFileChooser.getSelectedFile() + "' успешно сохранен");
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(AppFrame.this, "Error");
                    }
                }
            }
        });

        processbutton2.addActionListener(new ActionListener() {            //кнопка "выполнить" во вкладке JavaQueue дублирует элементы очереди из jdk
            @Override
            public void actionPerformed(ActionEvent e) {
                String str = inputQueue2.getText();                        //принимаем текст из поля ввода

                String[] queueItems = str.split("[\\s$]+");          //пробел будет считаться разделителем элементов
                Queue<String> queue = new LinkedList<>();

                for(String item : queueItems) {
                    queue.add(item);                                      //добавляем элементы в очередь
                }

                duplicateElementsForJavaQueue(queue);                     //дублируем элементы очереди Queue из jdk

                StringBuilder strBuilder = new StringBuilder();           //составляем ответ для вывода
                while(queue.size() > 0) {
                    try {
                        strBuilder.append(queue.poll());
                        if(queue.size() > 0) {
                            strBuilder.append(" ");
                        }
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }
                String result = strBuilder.toString();

                outputQueue2.setText(result);
            }
        });

        loadButton2.addActionListener(new ActionListener() {               //открытие файла во вкладке JavaQueue
            @Override
            public void actionPerformed(ActionEvent e) {
                if (openJFileChooser.showOpenDialog(AppFrame.this) == JFileChooser.APPROVE_OPTION) {
                    try (Scanner scan = new Scanner(openJFileChooser.getSelectedFile())) {
                        scan.useDelimiter("\\Z");
                        inputQueue2.setText(scan.next());
                    } catch (Exception ex) {
                        outputQueue2.setText("Error.(Чтобы считывался русский текст, нужно его сохранить в кодировке utf-8)");
                    }
                }
            }
        });

        savebutton2.addActionListener(new ActionListener() {                //сохранение результата в файле во вкладке JavaQueue
            @Override
            public void actionPerformed(ActionEvent e) {
                if(saveJFileChooser.showSaveDialog(AppFrame.this) == JFileChooser.APPROVE_OPTION) {
                    String filename = saveJFileChooser.getSelectedFile().getPath();
                    if (!filename.toLowerCase().endsWith(".txt")) {
                        filename += ".txt";
                    }
                    try (FileWriter wr = new FileWriter(filename)) {
                        wr.write(outputQueue2.getText());
                        JOptionPane.showMessageDialog(AppFrame.this,
                                "Файл '" + saveJFileChooser.getSelectedFile() + "' успешно сохранен");
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(AppFrame.this, "Error");
                    }
                }
            }
        });
    }

    public void duplicateElementsForJavaQueue(Queue<String> queue) {   //дублирует элементы очереди Queue из jdk
        int queueSize = queue.size();
        for(int i = 0; i < queueSize; i++) {
            queue.add(queue.element());
            queue.add(queue.element());
            queue.poll();
        }
    }
}
