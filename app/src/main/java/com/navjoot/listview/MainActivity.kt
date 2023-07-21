package com.navjoot.listview

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.navjoot.listview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    var array = mutableListOf<String>("A", "B", "C", "D")
    lateinit var adapter: ArrayAdapter<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, array)
        binding.listView.adapter = adapter

        binding.fab.setOnClickListener {
            var dialog = Dialog(this)
            dialog.setContentView(R.layout.adddialog)
            dialog.show()
            var etName = dialog.findViewById<EditText>(R.id.etName)
            var btnAdd = dialog.findViewById<Button>(R.id.btnAdd)

            btnAdd.setOnClickListener {
                if (etName.text.toString().isNullOrEmpty()) {
                    etName.error = "Enter your name"
                } else {
                    array.add(etName.text.toString())
                    adapter.notifyDataSetChanged()//it is used to tell list that there is changes in list
                    dialog.dismiss()
                }
            }

        }
        binding.listView.setOnItemClickListener { parent, view, position, id ->
            AlertDialog.Builder(this)
                .setTitle("Delete or update")
                .setMessage("Do you want to delete or update")
                .setPositiveButton("Delete", { _, _ ->
                    array.removeAt(position)
                    adapter.notifyDataSetChanged()
                })
               .setNegativeButton("Update", { _, _ ->
                    var dialogg = Dialog(this)
                    dialogg.setContentView(R.layout.update_dialog)
                   dialogg.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)//give size to dialog
                    dialogg.show()
                    var etNewName = dialogg.findViewById<EditText>(R.id.etNewName)
                    var btnUpdate = dialogg.findViewById<Button>(R.id.btnUpdate)

                    btnUpdate.setOnClickListener {
                        if (etNewName.text.toString().isNullOrEmpty()) {
                            etNewName.error = "Enter name"
                        } else {
                            array.set(position, etNewName.text.toString())
                            adapter.notifyDataSetChanged()
                            dialogg.dismiss()
                        }
                    }
                })
                .show()
        }

            binding.listView.setOnItemLongClickListener { parent, view, position, id ->
                array.removeAt(position)
                adapter.notifyDataSetChanged()
                return@setOnItemLongClickListener true
            }

        }
    }


