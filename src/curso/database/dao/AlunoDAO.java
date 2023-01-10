package curso.database.dao;

import curso.bean.Aluno;
import curso.database.SessionManager;

import java.util.ArrayList;
import java.util.List;

import static curso.util.StringUtils.isBlank;

public class AlunoDAO {

    public void gravar(Aluno aluno) {
        SessionManager.getInstance().save(aluno);
    }

    public void atualizar(Aluno aluno) {
        SessionManager.getInstance().update(aluno);
    }

    public void delete(Aluno aluno) {
        SessionManager.getInstance().delete(aluno);
    }

    public Aluno buscarPorCodigo(Integer code) {
        if (code == null)
            return null;

        return (Aluno) SessionManager.getInstance().searchByCode(Aluno.class, code);
    }

    public List<Aluno> buscarPorNome(String nome) {
        StringBuilder query = new StringBuilder();
        query.append("Aluno");
        if (!isBlank(nome)) {
            query.append(" ");
            query.append("where lower(nome) like '%");
            query.append(nome.toLowerCase());
            query.append("%'");
        }

        List<Object> lstQuery = SessionManager.getInstance().createQuery(query.toString());
        List<Aluno> lstReturn = new ArrayList<>();

        if (lstQuery == null) {
            return null;
        }

        for (Object obj : lstQuery) {
            if (obj instanceof Aluno)
                lstReturn.add((Aluno) obj);
        }

        return lstReturn;
    }
}