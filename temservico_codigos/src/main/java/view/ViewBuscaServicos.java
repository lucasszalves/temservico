package view;

import controller.ControllerAgendamentosUsuario;
import controller.ControllerBuscaServicos;
import controller.ParamsBuscaServico;
import controller.RetornoValidaMsg;
import model.Servico;
import model.TipoServico;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ViewBuscaServicos extends JFrame {
    private ControllerBuscaServicos controller;

    public ViewBuscaServicos(ControllerBuscaServicos controller){
        this.controller = controller;
    }

    public void janelaBusca() {
        setTitle("Buscar serviço");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(700, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel painelBusca = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

// 1. Título
        JLabel lblTitulo = new JLabel("Buscar Serviço");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 28));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2; // Ocupa as duas colunas
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(20, 10, 30, 10);
        painelBusca.add(lblTitulo, gbc);

// Resetando configurações do grid para os campos
        gbc.gridwidth = 1;
        gbc.insets = new Insets(10, 10, 10, 10);

// 2. Tipo
        JCheckBox chkTipo = new JCheckBox("Tipo:");
        chkTipo.setFont(new Font("SansSerif", Font.BOLD, 14));
        chkTipo.setHorizontalTextPosition(SwingConstants.LEFT); // Deixa o texto à esquerda da caixinha
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        painelBusca.add(chkTipo, gbc);

// Como você já tem o Enum TipoServico, podemos passar o .values() direto
        JComboBox<TipoServico> cbTipo = new JComboBox<>(TipoServico.values());
        cbTipo.setFont(new Font("SansSerif", Font.PLAIN, 14));
        cbTipo.setPreferredSize(new Dimension(250, 35));
        cbTipo.setEnabled(false);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        painelBusca.add(cbTipo, gbc);

        chkTipo.addActionListener(e -> cbTipo.setEnabled(chkTipo.isSelected()));

// 3. Cidades
        JCheckBox chkCidade = new JCheckBox("Cidade:");
        chkCidade.setFont(new Font("SansSerif", Font.BOLD, 14));
        chkCidade.setHorizontalTextPosition(SwingConstants.LEFT);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        painelBusca.add(chkCidade, gbc);

        JTextField txtCidade = new JTextField();
        txtCidade.setFont(new Font("SansSerif", Font.PLAIN, 16));
        txtCidade.setPreferredSize(new Dimension(250, 35));
        txtCidade.setEnabled(false);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        painelBusca.add(txtCidade, gbc);

        chkCidade.addActionListener(e -> txtCidade.setEnabled(chkCidade.isSelected()));

// 4. Preço Mínimo (Spinner)
        JCheckBox chkPrecoMin = new JCheckBox("Preço Mínimo:");
        chkPrecoMin.setFont(new Font("SansSerif", Font.BOLD, 14));
        chkPrecoMin.setHorizontalTextPosition(SwingConstants.LEFT);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        painelBusca.add(chkPrecoMin, gbc);

// Spinner configurado para Double (Valor inicial 0.0, min 0.0, max 99999.0, step 10.0)
        JSpinner spinPrecoMin = new JSpinner(new SpinnerNumberModel(0.0, 0.0, 99999.0, 10.0));
        spinPrecoMin.setEditor(new JSpinner.NumberEditor(spinPrecoMin, "0.00")); // Formata com duas casas decimais
        spinPrecoMin.setPreferredSize(new Dimension(250, 35));
        spinPrecoMin.setFont(new Font("SansSerif", Font.PLAIN, 16));
        spinPrecoMin.setEnabled(false);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        painelBusca.add(spinPrecoMin, gbc);

        chkPrecoMin.addActionListener(e -> spinPrecoMin.setEnabled(chkPrecoMin.isSelected()));

// 5. Preço Máximo (Spinner)
        JCheckBox chkPrecoMax = new JCheckBox("Preço Máximo:");
        chkPrecoMax.setFont(new Font("SansSerif", Font.BOLD, 14));
        chkPrecoMax.setHorizontalTextPosition(SwingConstants.LEFT);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.EAST;
        painelBusca.add(chkPrecoMax, gbc);

        JSpinner spinPrecoMax = new JSpinner(new SpinnerNumberModel(0.0, 0.0, 99999.0, 10.0));
        spinPrecoMax.setEditor(new JSpinner.NumberEditor(spinPrecoMax, "0.00"));
        spinPrecoMax.setPreferredSize(new Dimension(250, 35));
        spinPrecoMax.setFont(new Font("SansSerif", Font.PLAIN, 16));
        spinPrecoMax.setEnabled(false);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        painelBusca.add(spinPrecoMax, gbc);

        chkPrecoMax.addActionListener(e -> spinPrecoMax.setEnabled(chkPrecoMax.isSelected()));

// 6. Nota Mínima (Slider)
        JCheckBox chkNota = new JCheckBox("Nota Mínima:");
        chkNota.setFont(new Font("SansSerif", Font.BOLD, 14));
        chkNota.setHorizontalTextPosition(SwingConstants.LEFT);
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.EAST;
        painelBusca.add(chkNota, gbc);

// Sub-painel para o Slider e a Label dinâmica
        JPanel painelSlider = new JPanel(new BorderLayout(10, 0));

// JSlider vai de 0 a 500 para podermos dividir por 100 e ter 2 casas decimais
        JSlider sliderNota = new JSlider(0, 500, 0);
        sliderNota.setPreferredSize(new Dimension(200, 35));
        sliderNota.setEnabled(false);

        JLabel lblValorNota = new JLabel("0.00");
        lblValorNota.setFont(new Font("SansSerif", Font.BOLD, 16));
        lblValorNota.setEnabled(false); // Fica cinza quando o slider está desabilitado

        painelSlider.add(sliderNota, BorderLayout.CENTER);
        painelSlider.add(lblValorNota, BorderLayout.EAST);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        painelBusca.add(painelSlider, gbc);

// Atualiza a label em tempo real
        sliderNota.addChangeListener(e -> {
            double valorConvertido = sliderNota.getValue() / 100.0;
            // O String.format com locale US garante o ponto decimal ("1.02") em vez de vírgula
            lblValorNota.setText(String.format(java.util.Locale.US, "%.2f", valorConvertido));
        });

        chkNota.addActionListener(e -> {
            boolean selecionado = chkNota.isSelected();
            sliderNota.setEnabled(selecionado);
            lblValorNota.setEnabled(selecionado);
        });

// 7. Botão Buscar
        JButton btnBuscar = new JButton("Buscar!");
        btnBuscar.setFont(new Font("SansSerif", Font.BOLD, 18));
        btnBuscar.setPreferredSize(new Dimension(180, 45));
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2; // Volta a ocupar as duas colunas
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(30, 10, 20, 10); // Espaçamento maior em cima
        painelBusca.add(btnBuscar, gbc);

        btnBuscar.addActionListener(e -> {
            boolean buscaTipo = chkTipo.isSelected();
            boolean buscaCidade = chkCidade.isSelected();
            boolean buscaPrecoMin = chkPrecoMin.isSelected();
            boolean buscaPrecoMax = chkPrecoMax.isSelected();
            boolean buscaNota = chkNota.isSelected();
            ParamsBuscaServico params = new ParamsBuscaServico(buscaTipo, buscaCidade, buscaPrecoMin, buscaPrecoMax, buscaNota, (TipoServico) cbTipo.getSelectedItem(), txtCidade.getText(), (double) spinPrecoMin.getValue(), (double) spinPrecoMax.getValue(), (double) sliderNota.getValue() / 100);
            RetornoValidaMsg retorno = controller.validaParamsBusca(params);
            JOptionPane.showMessageDialog(this, retorno.mensagem());
            if(retorno.valido()){
                resultadosBusca();
            }
        });

        add(painelBusca);
        setVisible(true);
    }

    private void resultadosBusca() {
        ArrayList<Servico> servicosEncontrados = controller.getResultadoBusca();
        System.out.println(servicosEncontrados);
    }
}
