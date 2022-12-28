package curso.view.controller;

import curso.bean.Aluno;
import curso.database.SessionManager;
import curso.util.MessageGlobal;
import curso.view.TelaCadastroView;

import java.awt.*;

public class TelaCadastroController extends TelaCadastroView {

    public TelaCadastroController(Frame parent) {
        super(parent);
    }

    @Override
    protected void gravar(Aluno aluno) {

        if (!isRegistroValido(aluno)) {
            return;
        }

        if (aluno.getCodAluno() != null)
            gravarAlteracao(aluno);
        else
            gravarNovoRegistro(aluno);

    }

    @Override
    protected Integer getCodAlunoFromField(String strCodAluno) {
        if (strCodAluno == null) {
            return null;
        }

        if (strCodAluno.isBlank()) {
            return null;
        }

        Integer cod;
        try {
            cod = Integer.parseInt(strCodAluno);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            cod = null;
        }
        return cod;
    }

    @Override
    protected boolean isRegistroValido(Aluno aluno) {
        if (aluno.getNome() == null || aluno.getNome().isBlank()) {
            MessageGlobal.showErrorMessage("Informe o nome do aluno");
            return false;
        }

        if (aluno.getCurso() == null || aluno.getCurso().isBlank()) {
            MessageGlobal.showErrorMessage("Informe o curso");
            return false;
        }

        if (aluno.getCidade() == null || aluno.getCidade().isBlank()) {
            MessageGlobal.showErrorMessage("Informe a cidade");
            return false;
        }

        if (aluno.getTelefone() == null || aluno.getTelefone().isBlank()) {
            MessageGlobal.showErrorMessage("Informe o telefone");
            return false;
        }

        return true;
    }

    private void gravarAlteracao(Aluno aluno) {
        // Verifica se o houve alteração nos dados
        Aluno original = (Aluno) SessionManager.getInstance().searchByCode(Aluno.class, aluno.getCodAluno());

        if (original != null && aluno.equals(original)) {
            MessageGlobal.showInformationMessage("Não houve alteração nos registros.");
            return;
        }

        boolean confirma = MessageGlobal.showConfirmationDialog("Confirmar alteração de registro?");
        if (!confirma) {
            return;
        }

        try {
            SessionManager.getInstance().update(aluno);
        } catch (Exception ex) {
            MessageGlobal.showErrorMessage("Não foi alterar o registro", ex);
            return;
        }

        MessageGlobal.showInformationMessage("Registro alterado com sucesso!");
        closeWindow();
    }

    private void gravarNovoRegistro(Aluno aluno) {
        try {
            SessionManager.getInstance().save(aluno);
        } catch (Exception ex) {
            MessageGlobal.showErrorMessage("Não foi criar registro", ex);
            return;
        }

        MessageGlobal.showInformationMessage("Registro criado com sucesso!");
        closeWindow();
    }

}
