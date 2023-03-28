package Testes;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Testes {
    public static void main(String[] args) {
        String[] cidades = {"São Paulo", "Rio de Janeiro", "Belo Horizonte", "Curitiba", "Fortaleza"};

        JFrame frame = new JFrame("Escolha uma cidade");
        JPanel panel = new JPanel();
        JLabel label = new JLabel("Selecione uma cidade:");
        JComboBox<String> combo = new JComboBox<String>(cidades);

        combo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                String cidadeSelecionada = (String) combo.getSelectedItem();
                System.out.println("Você escolheu " + cidadeSelecionada);
                frame.dispose();
            }
        });

        panel.add(label);
        panel.add(combo);
        frame.add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

