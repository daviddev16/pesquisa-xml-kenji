package io.github.daviddev16;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.UIManager;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import java.awt.event.ActionEvent;

public class FramePrincipal extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField txtCusersdaviddownloadsnfesxmlxml;
    private JTable table;
    private JTextField txtsp;
    private JButton btnNewButton;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                    FramePrincipal frame = new FramePrincipal();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public FramePrincipal() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 504, 507);
        contentPane = new JPanel();
        contentPane.setBorder(null);
        setLocationRelativeTo(null);

        setContentPane(contentPane);
        
        txtCusersdaviddownloadsnfesxmlxml = new JTextField();
        txtCusersdaviddownloadsnfesxmlxml.setText("C:\\Users\\David\\Downloads\\Nfes_xml\\xml");
        txtCusersdaviddownloadsnfesxmlxml.setColumns(10);
        
        JScrollPane scrollPane = new JScrollPane();
        
        JButton btnNewButton_1 = new JButton("Processar Pesquisa");
        btnNewButton_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) 
            {
                table.clearSelection();
                
                DefaultTableModel tableModel = new DefaultTableModel();
                tableModel.addColumn("Arquivo");
                tableModel.addColumn("Trecho");
                tableModel.addColumn("Indice");
                
                File pastaXML = new File(txtCusersdaviddownloadsnfesxmlxml.getText());
                
                List<DadosPesquisa> dadosPesquisas = new ArrayList<DadosPesquisa>();
                
                try {
                    
                    List<String> trechosList = new ArrayList<String>();
                    
                    if (txtsp.getText().contains("&&"))
                    {
                        String[] sp = txtsp.getText().split("&&");
                        Stream.of(sp).map(s -> s.trim()).forEach(trechosList::add);
                    } else 
                    {
                        trechosList.add(txtsp.getText());
                    }
                    
                    busca(pastaXML, trechosList, dadosPesquisas);
                    
                    for (DadosPesquisa dadosPesquisa : dadosPesquisas)
                    {
                        tableModel.addRow(new Object[] { 
                                dadosPesquisa.getArquivo().getAbsolutePath(), 
                                dadosPesquisa.getTrecho(), 
                                Integer.toString(dadosPesquisa.getIndice()) });
                    }
                    
                    
                    table.setModel(tableModel);
                    
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

            }
        });
        
        txtsp = new JTextField();
        txtsp.setText("<UF>SP</UF>&&<CST>01</CST>");
        txtsp.setColumns(10);
        
        btnNewButton = new JButton("Dica");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) 
            {
                JOptionPane.showMessageDialog(null, "Utilize '&&' para pesquisar mais de um trecho. \n Exemplo: \n <UF>SP</UF> && <CST>01</CST>.");
            }
        });
        GroupLayout gl_contentPane = new GroupLayout(contentPane);
        gl_contentPane.setHorizontalGroup(
            gl_contentPane.createParallelGroup(Alignment.LEADING)
                .addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
                    .addGap(10)
                    .addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
                        .addComponent(txtCusersdaviddownloadsnfesxmlxml, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 483, Short.MAX_VALUE)
                        .addComponent(scrollPane, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 483, Short.MAX_VALUE)
                        .addGroup(gl_contentPane.createSequentialGroup()
                            .addComponent(txtsp, GroupLayout.DEFAULT_SIZE, 289, Short.MAX_VALUE)
                            .addPreferredGap(ComponentPlacement.UNRELATED)
                            .addComponent(btnNewButton_1)
                            .addPreferredGap(ComponentPlacement.RELATED)
                            .addComponent(btnNewButton)))
                    .addGap(81))
        );
        gl_contentPane.setVerticalGroup(
            gl_contentPane.createParallelGroup(Alignment.LEADING)
                .addGroup(gl_contentPane.createSequentialGroup()
                    .addGap(12)
                    .addComponent(txtCusersdaviddownloadsnfesxmlxml, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addGap(11)
                    .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                        .addComponent(txtsp, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnNewButton_1, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnNewButton))
                    .addGap(11)
                    .addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                    .addContainerGap())
        );
        
        table = new JTable();
        table.setColumnSelectionAllowed(true);
        table.setCellSelectionEnabled(true);
        scrollPane.setViewportView(table);
        contentPane.setLayout(gl_contentPane);
    }
    
    public static void busca(File pasta, List<String> trechos, 
            List<DadosPesquisa> dadosPesquisas) throws IOException
    {
        if (!pasta.isDirectory())
            throw new IllegalStateException(pasta.getAbsolutePath() + " não é uma pasta.");
        
        for (File arquivo : pasta.listFiles())
        {
            if (arquivo.isDirectory()) 
            {
                busca(arquivo, trechos, dadosPesquisas);
                continue;
            }
            dadosPesquisas.addAll(buscarTrechos(arquivo, trechos));
        }
    }
    
    public static List<DadosPesquisa> buscarTrechos(File arquivo, List<String> trechos) 
            throws IOException
    {
        List<DadosPesquisa> dadosPesquisas = new ArrayList<DadosPesquisa>();
        String conteudoArquivo = lerArquivo(arquivo).toLowerCase();
        for (String trecho : trechos)
        {
            String trechoTratado = trecho.trim().toLowerCase();
            int indiceTrecho = conteudoArquivo.indexOf(trechoTratado);
            if (indiceTrecho >= 0)
                dadosPesquisas.add(new DadosPesquisa(arquivo, trecho, indiceTrecho));
        }
        return dadosPesquisas;
    }
    
    private static String lerArquivo(File arquivo) throws IOException
    {
        BufferedReader reader = null;
        try 
        {
            reader = new BufferedReader(new FileReader(arquivo));
            StringBuilder builder = new StringBuilder(); 
            String linha;

            while((linha = reader.readLine()) != null)
                builder.append(linha).append("\n");
            
            return builder.toString();
        } finally 
        {
            fechar(reader);
        }
    }

    private static void fechar(Closeable closeable)
    {
        try {
            if (closeable != null)
                closeable.close();
        } catch (Exception e) {}
    }

    
}
