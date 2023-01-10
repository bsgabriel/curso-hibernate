package curso.view.controller;

import curso.bean.Aluno;
import curso.database.dao.AlunoDAO;
import curso.view.TelaCadastroView;

import java.awt.*;

import static curso.util.MessageGlobal.*;
import static curso.util.StringUtils.isBlank;
import static curso.util.StringUtils.stringToStringList;

public class TelaCadastroController extends TelaCadastroView {

    private AlunoDAO alunoDAO;

    private AlunoDAO getAlunoDAO() {
        if (alunoDAO == null)
            alunoDAO = new AlunoDAO();

        return alunoDAO;
    }

    public TelaCadastroController(Frame parent) {
        super(parent);
    }

    @Override
    protected void gravar(Aluno aluno) {

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

        if (isBlank(strCodAluno)) {
            return null;
        }

        int cod;
        try {
            cod = Integer.parseInt(strCodAluno);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return null;
        }
        return cod;
    }

    @Override
    protected boolean verificarCampos(String nome, String curso, String cidade, String telefones) {

        if (isBlank(nome)) {
            showErrorMessage("Informe o nome do aluno");
            return false;
        }

        if (isBlank(curso)) {
            showErrorMessage("Informe o curso");
            return false;
        }

        if (isBlank(cidade)) {
            showErrorMessage("Informe a cidade");
            return false;
        }

        if (isBlank(telefones)) {
            showErrorMessage("Informe um telefone");
            return false;
        }

        for (String telefone : stringToStringList(telefones)) {
            if (!isTelefoneValido(telefone))
                return false;

        }

        return true;
    }

    private void gravarAlteracao(Aluno alunoTmp) {
        // Busca o objeto persistente do banco
        Aluno alunoOriginal = getAlunoDAO().buscarPorCodigo(alunoTmp.getCodAluno());

        // Verifica se o objeto persistente é igual ao objeto temporário
        if (alunoTmp.equals(alunoOriginal)) {
            showInformationMessage("Não houve alteração no registro");
            return;
        }

        boolean confirma = showConfirmationDialog("Confirmar alteração de registro?");
        if (!confirma) {
            return;
        }

        // atualiza o objeto persistente
        try {
            alunoOriginal.setNome(alunoTmp.getNome());
            alunoOriginal.setCidade(alunoTmp.getCidade());
            alunoOriginal.setCurso(alunoTmp.getCurso());
            alunoOriginal.setTelefones(alunoTmp.getTelefones());
            getAlunoDAO().atualizar(alunoOriginal);
        } catch (Exception ex) {
            showErrorMessage("Erro alterar o registro", ex);
            return;
        }

        showInformationMessage("Registro alterado com sucesso!");
        closeWindow();
    }

    private void gravarNovoRegistro(Aluno aluno) {
        try {
            getAlunoDAO().gravar(aluno);
        } catch (Exception ex) {
            showErrorMessage("Erro ao criar registro", ex);
            return;
        }

        showInformationMessage("Registro criado com sucesso!");
        closeWindow();
    }

    private boolean isTelefoneValido(String telefone) {
        if (!telefone.matches("\\d+")) {
            showErrorMessage(createMessageTelefoneInvalido(telefone, "Telefone deve conter apenas números"));
            return false;
        }

        if (telefone.length() < 10 || telefone.length() > 11) {
            showErrorMessage(createMessageTelefoneInvalido(telefone, "Telefone deve ter ter 10 dígitos (DDD + telefone fixo) ou 11 dígitos (DDD + celular)"));
            return false;
        }

        return true;
    }

    private String createMessageTelefoneInvalido(String telefone, String descErro) {
        return "Telefone inválido: " + telefone + "\n" + descErro;
    }
}
