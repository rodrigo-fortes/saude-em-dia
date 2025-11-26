package com.example.saudeemdia

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class HealthTaskAdapter(
    private val itens: MutableList<HealthTask>,
    private val onItemClick: (HealthTask) -> Unit
) : RecyclerView.Adapter<HealthTaskAdapter.HealthTaskViewHolder>() {

    inner class HealthTaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtTitulo: TextView = itemView.findViewById(R.id.txtTitulo)
        val txtTipoData: TextView = itemView.findViewById(R.id.txtTipoData)
        val chkRealizado: CheckBox = itemView.findViewById(R.id.chkRealizado)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HealthTaskViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_health_task, parent, false)
        return HealthTaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: HealthTaskViewHolder, position: Int) {
        val task = itens[position]

        holder.txtTitulo.text = task.titulo
        holder.txtTipoData.text = "${task.tipo} - ${task.data}"
        holder.chkRealizado.isChecked = task.realizado

        holder.itemView.contentDescription =
            "${task.tipo} ${task.titulo} em ${task.data}. " +
                    if (task.realizado) "Já realizado" else "Pendente"

        holder.itemView.setOnClickListener {
            onItemClick(task)
        }

        holder.chkRealizado.setOnCheckedChangeListener { _, isChecked ->
            task.realizado = isChecked
            notifyItemChanged(position)
        }
    }

    override fun getItemCount(): Int = itens.size
}
