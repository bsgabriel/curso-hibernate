package curso.view.controller;

import curso.bean.Aluno;
import curso.database.SessionManager;
import curso.view.MenuPrincipalView;

import java.util.ArrayList;
import java.util.List;

import static curso.util.StringUtils.*;

public class MenuPrincipalController extends MenuPrincipalView {
    String lastSearch = "";

    @Override
    protected void actBuscar(String filter) {
        buscar(filter, false);
    }

    @Override
    protected void actExcluir(Aluno aluno) {
        if (aluno == null || aluno.getCodAluno() == null) {
            return;
        }
        SessionManager.getInstance().delete(aluno);
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

    /**
     * Efetua a busca no banco de dados e exibe os itens na tabela
     *
     * @param filter           Nome do aluno que será usado para filtrar.
     * @param ignoreLastSearch Indica se deve ignorar a última busca. Se for false, só efetuará a busca caso o filtro atual e o da busca anterior sejam diferentes.
     */
    private void buscar(String filter, boolean ignoreLastSearch) {
        if (filter == null) {
            filter = "";
        }

        if (filter.equals(lastSearch) && !ignoreLastSearch) {
            return;
        }

        StringBuilder query = new StringBuilder();
        query.append("Aluno");
        if (!isBlank(filter)) {
            query.append(" ");
            query.append("where lower(nome) like '%");
            query.append(filter.toLowerCase());
            query.append("%'");
        }

        lastSearch = query.toString();
        List<Object> lstQuery = SessionManager.getInstance().createQuery(query.toString());
        List<Aluno> lstReturn = new ArrayList<>();

        if (lstQuery == null) {
            return;
        }

        for (Object obj : lstQuery) {
            if (obj instanceof Aluno) {
                lstReturn.add((Aluno) obj);
            }
        }


        super.clearTable();

        if (lstReturn.isEmpty()) {
            return;
        }

        for (Aluno aluno : lstReturn) {
            super.addRow(new Object[]{aluno.getCodAluno(), aluno.getNome(), aluno.getCurso(), stringListToString(aluno.getTelefones()), aluno.getCidade()});
        }
    }

}
