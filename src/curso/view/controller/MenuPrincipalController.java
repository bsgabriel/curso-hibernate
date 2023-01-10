package curso.view.controller;

import curso.bean.Aluno;
import curso.database.dao.AlunoDAO;
import curso.view.MenuPrincipalView;

import java.util.List;

import static curso.util.StringUtils.*;

public class MenuPrincipalController extends MenuPrincipalView {
    private AlunoDAO alunoDAO;
    private String lastSearch = "";

    private AlunoDAO getAlunoDAO() {
        if (alunoDAO == null)
            alunoDAO = new AlunoDAO();

        return alunoDAO;
    }

    @Override
    protected void actExcluir(Aluno aluno) {
        if (aluno == null || aluno.getCodAluno() == null) {
            return;
        }
        getAlunoDAO().delete(aluno);
        buscar(getSearchFieldContent(), true);
    }

    @Override
    protected void actExibirTelaCadastro(Aluno aluno) {
        TelaCadastroController tela = new TelaCadastroController(this);
        if (aluno != null) {
            tela.carregarAluno(aluno);
        }
        tela.setVisible(true);
        buscar(getSearchFieldContent(), true);
    }

    @Override
    protected void actBuscar(String filter) {
        buscar(filter, false);
    }

    @Override
    protected String createContentTelefone(String strTelefones) {
        StringBuilder msg = new StringBuilder();
        for (String telefone : stringToStringList(strTelefones)) {
            msg.append(telefone);
            msg.append("\n");
        }
        return msg.toString();
    }

    protected Aluno getRowData(int row) {
        if (row == -1) {
            return null;
        }

        Integer codAluno = (Integer) getModelAlunos().getValueAt(row, 0);
        return getAlunoDAO().buscarPorCodigo(codAluno);
    }

    /**
     * Efetua a busca no banco de dados e exibe os itens na tabela
     *
     * @param filter           Nome do aluno que será usado para filtrar.
     * @param ignoreLastSearch Indica se deve ignorar a última busca. Se for false, só efetuará a busca caso o filtro atual e o da busca anterior sejam diferentes.
     */
    private void buscar(String filter, boolean ignoreLastSearch) {
        if (isBlank(filter))
            filter = "";

        if (filter.equals(lastSearch) && !ignoreLastSearch)
            return;

        lastSearch = filter;
        List<Aluno> lstReturn = getAlunoDAO().buscarPorNome(filter);

        super.clearTable();

        if (lstReturn.isEmpty())
            return;

        for (Aluno aluno : lstReturn) {
            super.addRow(new Object[]{aluno.getCodAluno(), aluno.getNome(), aluno.getCurso(), stringListToString(aluno.getTelefones()), aluno.getCidade()});
        }
    }

}
