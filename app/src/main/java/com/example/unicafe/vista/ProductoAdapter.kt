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
    private val onProductoClick: (Producto) -> Unit
) : RecyclerView.Adapter<ProductoAdapter.ProductoViewHolder>() {

    inner class ProductoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgProducto: ImageView = itemView.findViewById(R.id.img)
        val txtNombre: TextView = itemView.findViewById(R.id.txtNombreProducto)
        val txtPrecio: TextView = itemView.findViewById(R.id.txt)

        fun bind(producto: Producto) {
            txtNombre.text = producto.nombre
            txtPrecio.text = "$${producto.precio}"

            // Cargar imagen con Glide (asegúrate de tener Glide en tu build.gradle)
            Glide.with(itemView.context)
                .load(producto.imagen)
                .placeholder(R.drawable.ic_placeholder) // Imagen por defecto
                .error(R.drawable.ic_error) // Imagen si hay error
                .into(imgProducto)

            // Configurar click listener
            itemView.setOnClickListener {
                onProductoClick(producto)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_alimentos, parent, false)
        return ProductoViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductoViewHolder, position: Int) {
        holder.bind(listaProductos[position])
    }

    override fun getItemCount(): Int = listaProductos.size

    fun actualizarDatos(nuevaLista: List<Producto>) {
        listaProductos = nuevaLista
        notifyDataSetChanged()
    }
}