package com.example.unicafe.vista

import android.content.Intent
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
    private var productos: List<Producto>
) : RecyclerView.Adapter<ProductoAdapter.ProductoViewHolder>() {

    inner class ProductoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgProducto: ImageView = itemView.findViewById(R.id.imgDetailAliments)
        val txtNombre: TextView = itemView.findViewById(R.id.txtNombDetailAliments)
        val txtDescripcion: TextView = itemView.findViewById(R.id.txtDescripDetailAliments)
        val txtPrecio: TextView = itemView.findViewById(R.id.txtPrecioDetailAliments)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_detalles_alimentos, parent, false)
        return ProductoViewHolder(view)
    }

    override fun getItemCount(): Int = productos.size

    override fun onBindViewHolder(holder: ProductoViewHolder, position: Int) {
        val producto = productos[position]

        holder.txtNombre.text = producto.nombre
        holder.txtDescripcion.text = producto.descripcion
        holder.txtPrecio.text = "$ ${producto.precio}"

        Glide.with(holder.itemView.context)
            .load(producto.url_imagen)
            .into(holder.imgProducto)

        holder.itemView.setOnClickListener {
            val ctx = holder.itemView.context
            val intent = Intent(ctx, DetallesAlimentos::class.java)
            intent.putExtra("NOMBRE", producto.nombre)
            intent.putExtra("DESCRIPCION", producto.descripcion)
            intent.putExtra("PRECIO", producto.precio)
            intent.putExtra("IMAGEN_URL", producto.url_imagen)
            ctx.startActivity(intent)
        }
    }

    fun actualizarDatos(nuevaLista: List<Producto>) {
        productos = nuevaLista
        notifyDataSetChanged()
    }
}