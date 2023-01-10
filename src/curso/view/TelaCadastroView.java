package curso.view;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import curso.bean.Aluno;
import curso.util.ComponentFactory;

import javax.swing.*;
import java.awt.*;

import static curso.util.StringUtils.stringListToString;
import static curso.util.StringUtils.stringToStringList;

public abstract class TelaCadastroView extends JDialog {
    private JTextField fldCodigo;
    private JTextField fldNome;
    private JTextField fldTelefone;
    private JTextField fldCidade;
    private JTextField fldCurso;
    private JButton btnConfirmar;
    private JButton btnCancelar;
    private JButton btnLimpar;

    protected abstract void gravar(Aluno aluno);

    protected abstract Integer getCodAlunoFromField(String strCodAluno);

    protected abstract boolean verificarCampos(String nome, String curso, String cidade, String telefones);

    protected TelaCadastroView(Frame parent) {
        super(parent, "Cadastrador - Alteração/Cadastro", true);

        this.setSize(400, 400);
        this.setResizable(false);
        this.setLocationRelativeTo(null);

        String colunas = "10px, pref:grow, 10px";
        String linhas = "10px, fill:pref:grow, 10px, 50px, 10px";
        this.setLayout(new FormLayout(colunas, linhas));

        initComponents();
        CellConstraints cc = new CellConstraints();
        this.add(createFieldPnl(), cc.xy(2, 2));
        this.add(createButtonPnl(), cc.xy(2, 4));
        this.addEvents();
    }

    private void initComponents() {
        this.fldCodigo = new JTextField();
        this.fldCodigo.setEnabled(false);
        this.fldNome = new JTextField();
        this.fldTelefone = new JTextField();
        this.fldCidade = new JTextField();
        this.fldCurso = new JTextField();
        this.btnConfirmar = new JButton("Confirmar");
        this.btnCancelar = new JButton("Cancelar");
        this.btnLimpar = new JButton("Limpar");
    }

    private JPanel createFieldPnl() {
        JPanel pnl = new JPanel();
        pnl.setLayout(new BoxLayout(pnl, BoxLayout.Y_AXIS));

        pnl.add(ComponentFactory.createFieldWithLabel("Código", fldCodigo));
        pnl.add(Box.createRigidArea(new Dimension(0, 10)));
        pnl.add(ComponentFactory.createFieldWithLabel("Nome", fldNome));
        pnl.add(Box.createRigidArea(new Dimension(0, 10)));
        pnl.add(ComponentFactory.createFieldWithLabel("Telefone(s)", fldTelefone));
        pnl.add(Box.createRigidArea(new Dimension(0, 10)));
        pnl.add(ComponentFactory.createFieldWithLabel("Cidade", fldCidade));
        pnl.add(Box.createRigidArea(new Dimension(0, 10)));
        pnl.add(ComponentFactory.createFieldWithLabel("Curso", fldCurso));

        return pnl;
    }

    private JPanel createButtonPnl() {
        JPanel pnl = new JPanel();
        pnl.setLayout(new BoxLayout(pnl, BoxLayout.X_AXIS));

        pnl.add(btnConfirmar);
        pnl.add(Box.createRigidArea(new Dimension(10, 0)));
        pnl.add(btnCancelar);
        pnl.add(Box.createRigidArea(new Dimension(10, 0)));
        pnl.add(btnLimpar);
        return pnl;
    }

    private void addEvents() {
        this.btnConfirmar.addActionListener(actionEvent -> actionConfirmar());
        this.btnCancelar.addActionListener(actionEvent -> closeWindow());
        this.btnLimpar.addActionListener(actionEvent -> limparCampos());
    }

    private void actionConfirmar() {
        if (!verificarCampos(fldNome.getText(), fldCurso.getText(), fldCidade.getText(), fldTelefone.getText()))
            return;

        Aluno aluno = new Aluno();

        Integer cod = getCodAlunoFromField(fldCodigo.getText());
        aluno.setCodAluno(cod);
        aluno.setNome(fldNome.getText());
        aluno.setCidade(fldCidade.getText());
        aluno.setCurso(fldCurso.getText());
        aluno.getTelefones().addAll(stringToStringList(fldTelefone.getText()));
        gravar(aluno);
    }

    protected void closeWindow() {
        this.dispose();
    }

    private void limparCampos() {
        fldCodigo.setText("");
        fldNome.setText("");
        fldCurso.setText("");
        fldTelefone.setText("");
        fldCidade.setText("");
    }

    public void carregarAluno(Aluno aluno) {
        if (aluno == null) {
            return;
        }

        fldCodigo.setText(String.valueOf(aluno.getCodAluno()));
        fldNome.setText(aluno.getNome());
        fldTelefone.setText(stringListToString(aluno.getTelefones()));
        fldCidade.setText(aluno.getCidade());
        fldCurso.setText(aluno.getCurso());

        btnLimpar.setVisible(false);
    }
}