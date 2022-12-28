package curso.view;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import curso.bean.Aluno;
import curso.view.tablemodel.TableModelAluno;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public abstract class MenuPrincipalView extends JFrame {
    protected abstract void actExcluir(Aluno aluno);

    protected abstract void actExibirTelaCadastro(Aluno aluno);

    protected abstract void actBuscar(String filtro);

    private JButton btnPesquisar;
    private JButton btnNovo;
    private JButton btnExcluir;
    private JButton btnEditar;
    private JTextField fldPesquisa;
    private JTable tblAlunos;

    private TableModelAluno modelAlunos;
    private CellConstraints cc;
    private int previousSelectedRow;

    protected MenuPrincipalView() {
        this.setTitle("Cadastrador");

        this.setSize(1000, 600);
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        String colunas = "10px, pref:grow, 10px";
        String linhas = "10px, pref, 10px, fill:pref:grow, 10px, pref, 10px";
        this.setLayout(new FormLayout(colunas, linhas));

        initComponents();
        this.add(createSearchPnl(), cc.xy(2, 2));
        this.add(new JScrollPane(tblAlunos), cc.xy(2, 4));
        this.add(createButtonPnl(), cc.xy(2, 6));
        this.addEvents();
        this.previousSelectedRow = tblAlunos.getSelectedRow();
        actBuscar(getSearchFieldContent());
    }

    private void initComponents() {
        this.btnNovo = new JButton("Novo");
        this.btnExcluir = new JButton("Remover");
        this.btnEditar = new JButton("Editar");
        this.btnPesquisar = new JButton("Buscar");
        this.fldPesquisa = new JTextField();
        this.cc = new CellConstraints();

        // inicialização da tabela
        this.modelAlunos = new TableModelAluno();
        this.tblAlunos = new JTable(modelAlunos);
        this.modelAlunos.addColumn("Código");
        this.modelAlunos.addColumn("Nome");
        this.modelAlunos.addColumn("Curso");
        this.modelAlunos.addColumn("Fone");
        this.modelAlunos.addColumn("Cidade");
        this.tblAlunos.getColumnModel().getColumn(0).setPreferredWidth(10);
        this.tblAlunos.getColumnModel().getColumn(1).setPreferredWidth(100);
        this.tblAlunos.getColumnModel().getColumn(2).setPreferredWidth(100);
        this.tblAlunos.getColumnModel().getColumn(3).setPreferredWidth(100);
        this.tblAlunos.getColumnModel().getColumn(4).setPreferredWidth(100);
    }

    private JPanel createSearchPnl() {
        JPanel pnl = new JPanel();
        String col = "pref:grow, 10px, 100px";
        String row = "25px, fill:pref:grow";
        pnl.setLayout(new FormLayout(col, row));
        pnl.add(new JLabel("Filtrar por nome"), cc.xy(1, 1));
        pnl.add(fldPesquisa, cc.xy(1, 2));
        pnl.add(btnPesquisar, cc.xy(3, 2));
        return pnl;
    }

    private JPanel createButtonPnl() {
        JPanel pnl = new JPanel();
        String col = "pref:grow, 10px, pref:grow, 10px, pref:grow";
        pnl.setLayout(new FormLayout(col, "fill:pref:grow"));
        pnl.add(btnNovo, cc.xy(1, 1));
        pnl.add(btnEditar, cc.xy(3, 1));
        pnl.add(btnExcluir, cc.xy(5, 1));
        return pnl;
    }

    private void addEvents() {
        this.btnNovo.addActionListener(actionEvent -> actExibirTelaCadastro(null));

        this.btnEditar.addActionListener(actionEvent -> {
            if (tblAlunos.getSelectedRow() == -1) {
                return;
            }
            actExibirTelaCadastro(this.getSelectedRowData());
        });

        this.btnExcluir.addActionListener(actionEvent -> {
            int selectedRow = tblAlunos.getSelectedRow();

            if (selectedRow == -1) {
                return;
            }

            if (selectedRow == previousSelectedRow) {
                tblAlunos.clearSelection();
                return;
            }

            previousSelectedRow = selectedRow;

            actExcluir(getSelectedRowData());
        });

        this.btnPesquisar.addActionListener(actionEvent -> actBuscar(getSearchFieldContent()));

        this.tblAlunos.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent mouseEvent) {
                if (mouseEvent.getClickCount() != 2) {
                    return;
                }
                
                if (tblAlunos.getSelectedRow() == -1) {
                    return;
                }

                actExibirTelaCadastro(getSelectedRowData());
            }
        });
    }

    private Aluno getSelectedRowData() {
        int selectedLine = tblAlunos.getSelectedRow();

        if (selectedLine == -1) {
            return null;
        }

        Integer codAluno = (Integer) modelAlunos.getValueAt(selectedLine, 0);

        Aluno aluno = new Aluno();
        aluno.setCodAluno(codAluno);
        aluno.setNome((String) modelAlunos.getValueAt(selectedLine, 1));
        aluno.setCurso((String) modelAlunos.getValueAt(selectedLine, 2));
        aluno.setTelefone((String) modelAlunos.getValueAt(selectedLine, 3));
        aluno.setCidade((String) modelAlunos.getValueAt(selectedLine, 4));
        return aluno;
    }

    protected void clearTable() {
        modelAlunos.setRowCount(0);
    }

    protected void addRow(Object[] row) {
        this.modelAlunos.addRow(row);
    }

    protected String getSearchFieldContent() {
        String search = fldPesquisa.getText();
        if (search == null) {
            search = "";
        }
        return search;
    }
}
