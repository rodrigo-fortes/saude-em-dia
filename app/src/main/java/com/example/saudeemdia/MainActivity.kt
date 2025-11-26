package com.example.saudeemdia  // use o mesmo pacote dos outros arquivos

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var fabAdd: FloatingActionButton
    private lateinit var adapter: HealthTaskAdapter

    private val itens: MutableList<HealthTask> = mutableListOf()
    private var proximoId: Long = 1L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerViewTasks)
        fabAdd = findViewById(R.id.fabAdd)

        adapter = HealthTaskAdapter(itens) { task ->
            // Ao clicar no item, alterna o status realizado
            task.realizado = !task.realizado
            adapter.notifyDataSetChanged()

            val msg = if (task.realizado) {
                "Marcado como realizado."
            } else {
                "Marcado como pendente."
            }
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        fabAdd.setOnClickListener {
            mostrarDialogoNovoItem()
        }

        // Item de exemplo inicial (opcional)
        itens.add(
            HealthTask(
                id = proximoId++,
                titulo = "Check-up anual",
                tipo = "Consulta",
                data = "10/12/2025",
                realizado = false
            )
        )
        adapter.notifyDataSetChanged()
    }

    private fun mostrarDialogoNovoItem() {
        val dialogView = LayoutInflater.from(this)
            .inflate(R.layout.dialog_nova_tarefa, null)

        val edtTitulo = dialogView.findViewById<EditText>(R.id.edtTitulo)
        val edtData = dialogView.findViewById<EditText>(R.id.edtData)
        val spinnerTipo = dialogView.findViewById<Spinner>(R.id.spinnerTipo)

        val tipos = listOf("Consulta", "Exame")
        val adapterSpinner = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            tipos
        )
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerTipo.adapter = adapterSpinner

        val dialog = AlertDialog.Builder(this)
            .setTitle("Novo lembrete de saúde")
            .setView(dialogView)
            .setPositiveButton("Salvar") { _, _ ->
                val titulo = edtTitulo.text.toString().trim()
                val data = edtData.text.toString().trim()
                val tipo = spinnerTipo.selectedItem.toString()

                if (titulo.isEmpty() || data.isEmpty()) {
                    Toast.makeText(
                        this,
                        "Preencha título e data.",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    val task = HealthTask(
                        id = proximoId++,
                        titulo = titulo,
                        tipo = tipo,
                        data = data,
                        realizado = false
                    )
                    itens.add(task)
                    adapter.notifyItemInserted(itens.size - 1)
                }
            }
            .setNegativeButton("Cancelar", null)
            .create()

        dialog.show()
    }
}
