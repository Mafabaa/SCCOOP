package com.example.smacc

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FAQAdapter(private var faqList: List<FAQData.FAQItem>) :
    RecyclerView.Adapter<FAQAdapter.FAQViewHolder>() {

    private var expandedPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FAQViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.faq_item, parent, false)
        return FAQViewHolder(view)
    }

    override fun onBindViewHolder(holder: FAQViewHolder, position: Int) {
        val faqItem = faqList[position]
        holder.questionTextView.text = faqItem.pertanyaan
        holder.answerTextView.text = faqItem.jawaban

        val isExpanded = position == expandedPosition
        holder.answerTextView.visibility = if (isExpanded) View.VISIBLE else View.GONE
        holder.arrowImageView.setImageResource(
            if (isExpanded) R.drawable.ic_baseline_keyboard_arrow_up_24 else R.drawable.ic_baseline_keyboard_arrow_down_24
        )

        // Visibilitas garis vertikal dan horisontal
        holder.horizontalLineBtm.visibility = if (isExpanded) View.VISIBLE else View.GONE
        holder.horizontalLineTop.visibility = if (isExpanded) View.VISIBLE else View.GONE

        holder.itemView.setOnClickListener {
            expandedPosition = if (isExpanded) -1 else position
            notifyItemChanged(position)
        }
    }

    override fun getItemCount(): Int {
        return faqList.size
    }

    inner class FAQViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val questionTextView: TextView = itemView.findViewById(R.id.textQuestion)
        val answerTextView: TextView = itemView.findViewById(R.id.textAnswer)
        val arrowImageView: ImageView = itemView.findViewById(R.id.imageArrowFAQ)

        // Refrensi Garis Horizontal dan Vertikal
        val horizontalLineBtm: View = itemView.findViewById(R.id.horizontalLineBtm)
        val horizontalLineTop: View = itemView.findViewById(R.id.horizontalLineTop)
    }

    fun updateList(filteredFaqList: List<FAQData.FAQItem>) {
        this.faqList = filteredFaqList
        notifyDataSetChanged()
    }
}


