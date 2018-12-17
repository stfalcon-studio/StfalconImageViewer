package com.stfalcon.sample.features.main.card

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.stfalcon.sample.R
import kotlinx.android.synthetic.main.fragment_demo_card.*

class DemoCardFragment : Fragment() {

    companion object {
        private const val ARG_ACTION_ID = "ARG_ACTION_ID"
        private const val ARG_TITLE = "ARG_TITLE"
        private const val ARG_DESCRIPTION = "ARG_DESCRIPTION"

        fun newInstance(actionId: Int, title: String, description: String) =
            DemoCardFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_ACTION_ID, actionId)
                    putString(ARG_TITLE, title)
                    putString(ARG_DESCRIPTION, description)
                }
            }
    }

    private val actionId: Int
        get() = arguments?.getInt(ARG_ACTION_ID) ?: -1

    private val title: String
        get() = arguments?.getString(ARG_TITLE) ?: ""

    private val description: String
        get() = arguments?.getString(ARG_DESCRIPTION) ?: ""

    private var cardActionListener: OnCardActionListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_demo_card, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        demoCardTitleText.text = title
        demoCardDescriptionText.text = description
        demoCardActionButton.setOnClickListener { cardActionListener?.onCardAction(actionId) }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnCardActionListener) {
            cardActionListener = context
        } else {
            throw RuntimeException(context.toString() + " must to implement the OnCardActionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        cardActionListener = null
    }

    interface OnCardActionListener {
        fun onCardAction(actionId: Int)
    }
}