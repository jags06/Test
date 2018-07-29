package com.jags.arunkumar.androidtest.UI.Fragment

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.Snackbar
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout.VERTICAL
import android.widget.ProgressBar
import android.widget.TextView
import com.jags.arunkumar.androidtest.ExtensionPackage.replaceFragment
import com.jags.arunkumar.androidtest.R
import com.jags.arunkumar.androidtest.UI.Activity.DeliveryActivity
import com.jags.arunkumar.androidtest.UI.Adapter.DeliveryListAdapter
import com.jags.arunkumar.androidtest.ViewModel.DeliveryViewModel
import com.jags.arunkumar.androidtest.data.DeliveryData
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.delivery_data.view.*
import org.jetbrains.anko.support.v4.find
import javax.inject.Inject


class DeliveryFragment @Inject constructor() : DaggerFragment() {


    @Inject
    lateinit var deliveryViewModel: DeliveryViewModel
    @Inject
    lateinit var mapFragment: MapFragment
    lateinit var progressBar: ProgressBar
    lateinit var recyclerView: RecyclerView
    private val coordinatorLayout: CoordinatorLayout by lazy { find<CoordinatorLayout>(R.id.fragment_node_root_coordinatorLayout) }
    private var snackbar: Snackbar? = null
    override fun onAttach(context: Context?) {
        super.onAttach(context)
        (activity as DeliveryActivity).setupTitleBar("Things to Deliver")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.delivery_data, container, false)

        initRecyclerView(view)
        requestData()
        subscribeDeliveryData()
        subscribeErrorMessasge()

        return view
    }

    private fun initRecyclerView(view: View) {
        progressBar = view.progressBar.findViewById(R.id.progressBar)
        recyclerView = view.recyclerView.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(DeliveryFragment@ this.context)
        recyclerView.setHasFixedSize(true)
        val decoration = DividerItemDecoration(DeliveryFragment@ this.context, VERTICAL)
        recyclerView.addItemDecoration(decoration)

    }

    private fun requestData() {
        progressBar.visibility = View.VISIBLE
        deliveryViewModel.getRequestData()
    }

    private fun subscribeDeliveryData() {
        deliveryViewModel.provideList.subscribe {
            progressBar.visibility = View.GONE
            recyclerView.adapter =
                    DeliveryListAdapter(it, object : DeliveryListAdapter.OnItemClickListener {
                        override fun onItemClick(item: DeliveryData) {
                            val bundle = Bundle()
                            bundle.putParcelable("Key", item)
                            mapFragment.arguments = bundle
                            (activity as DeliveryActivity).replaceFragment(
                                mapFragment,
                                R.id.fragment_container
                            )

                        }
                    })
        }
    }

    private fun subscribeErrorMessasge() {
        deliveryViewModel.provideError.subscribe {
            progressBar.visibility = View.GONE
            showSnackBar(it)
        }
    }

    private fun showSnackBar(string: String) {
        snackbar?.dismiss()
        snackbar = Snackbar
            .make(coordinatorLayout, string, Snackbar.LENGTH_INDEFINITE)
        snackbar?.setAction("Retry") {
            requestData()
        }

        snackbar?.setActionTextColor(Color.YELLOW)
        val sbView = snackbar?.view
        val textView = sbView?.findViewById(android.support.design.R.id.snackbar_text) as TextView
        textView.setTextColor(Color.WHITE)

        snackbar?.show()
    }
}