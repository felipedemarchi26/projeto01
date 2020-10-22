package br.senai.sc.projeto01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import br.senai.sc.projeto01.database.CategoriaDAO;
import br.senai.sc.projeto01.database.ProdutoDAO;
import br.senai.sc.projeto01.modelo.Categoria;
import br.senai.sc.projeto01.modelo.Produto;

public class CadastroProdutoActivity extends AppCompatActivity {

    private int id = 0;
    private Spinner spinnerCategorias;
    private ArrayAdapter<Categoria> categoriasAdapter;
    private EditText editTextNome;
    private EditText editTextValor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_produto);
        setTitle("Cadastro de Produto");
        spinnerCategorias = findViewById(R.id.spinner_categorias);
        editTextNome = findViewById(R.id.editText_nome);
        editTextValor = findViewById(R.id.editText_valor);
        carregarCategorias();
        carregarProduto();
    }

    private void carregarCategorias() {
        CategoriaDAO categoriaDao = new CategoriaDAO(getBaseContext());
        categoriasAdapter = new ArrayAdapter<Categoria>(CadastroProdutoActivity.this,
                android.R.layout.simple_spinner_item,
                categoriaDao.listar());
        spinnerCategorias.setAdapter(categoriasAdapter);
    }

    private void carregarProduto() {
        Intent intent = getIntent();
        if (intent != null && intent.getExtras() != null &&
                intent.getExtras().get("produtoEdicao") != null) {
            Produto produto  = (Produto) intent.getExtras().get("produtoEdicao");
            editTextNome.setText(produto.getNome());
            editTextValor.setText(String.valueOf(produto.getValor()));
            int posicaoCategoria = obterPosicaoCategoria(produto.getCategoria());
            spinnerCategorias.setSelection(posicaoCategoria);
            id = produto.getId();
        }
    }

    private int obterPosicaoCategoria(Categoria categoria) {
        for (int posicao = 0; posicao < categoriasAdapter.getCount(); posicao++) {
            if (categoriasAdapter.getItem(posicao).getId() == categoria.getId()) {
                return posicao;
            }
        }
        return 0;
    }

    public void onClickVoltar(View v) {
        finish();
    }

    public void onClickSalvar(View v) {
        String nome = editTextNome.getText().toString();
        Float valor = Float.parseFloat(editTextValor.getText().toString());
        //Categoria categoria = (Categoria) spinnerCategorias.getSelectedItem();
        int posicaoCategoria = spinnerCategorias.getSelectedItemPosition();
        Categoria categoria = (Categoria) categoriasAdapter.getItem(posicaoCategoria);
        Produto produto = new Produto(id, nome, valor, categoria);
        ProdutoDAO produtoDao = new ProdutoDAO(getBaseContext());
        boolean salvou = produtoDao.salvar(produto);
        if (salvou) {
            finish();
        } else {
            Toast.makeText(CadastroProdutoActivity.this, "Erro ao salvar", Toast.LENGTH_LONG).show();
        }

    }

}
