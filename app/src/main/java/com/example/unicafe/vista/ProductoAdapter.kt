package com.example.unicafe.vista

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.unicafe.R
import com.example.unicafe.modelo.dto.Producto

class ProductoAdapter(
    private var listaProductos: List<Producto>,
    private val onItemClick: (Producto) -> Unit
) : RecyclerView.Adapter<ProductoAdapter.ProductoViewHolder>() {

    inner class ProductoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgProducto: ImageView = itemView.findViewById(R.id.imgProducto)
        val txtNombre: TextView = itemView.findViewById(R.id.txtNombreProducto)
        val txtDescripcion: TextView = itemView.findViewById(R.id.txtDescripcionProducto)
        val txtPrecio: TextView = itemView.findViewById(R.id.txtPrecioProducto)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_producto, parent, false)
        return ProductoViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductoViewHolder, position: Int) {
        val producto = listaProductos[position]

        holder.txtNombre.text = producto.nombre
        holder.txtDescripcion.text = producto.descripcion
        holder.txtPrecio.text = "$${String.format("%.2f", producto.precio)}"

        // En tu data class es url_imagen (con guion bajo)
        val imagenUrl = producto.url_imagen
        if (!imagenUrl.isNullOrEmpty()) {
            Glide.with(holder.itemView.context)
                .load(imagenUrl)
                .placeholder(R.mipmap.ic_launcher)
                .into(holder.imgProducto)
        } else {
            holder.imgProducto.setImageResource(R.mipmap.ic_launcher)
        }

        holder.itemView.setOnClickListener {
            onItemClick(producto)
        }
    }

    override fun getItemCount(): Int = listaProductos.size

    // Nombre alineado con lo que usamos en Alimentos.kt
    fun actualizarDatos(nuevaLista: List<Producto>) {
        listaProductos = nuevaLista
        notifyDataSetChanged()
    }
}
