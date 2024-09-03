package com.org.marton.studio.project.eldarwallet.ui.activities.addcard

import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.org.marton.studio.project.eldarwallet.R
import com.org.marton.studio.project.eldarwallet.domain.model.Bank
import com.org.marton.studio.project.eldarwallet.domain.model.CardType
import com.org.marton.studio.project.eldarwallet.utils.CardUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddDigitalCardActivity : AppCompatActivity() {

    private val viewModel: AddDigitalCardViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_digital_card)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val cardNumberTextView = findViewById<TextView>(R.id.cardNumberTextView)
        val cardClientNameTextView = findViewById<TextView>(R.id.cardClientNameTextView)
        val cardExpiryDateTextView = findViewById<TextView>(R.id.cardExpiryDateTextView)
        val cardTypeImageView = findViewById<TextView>(R.id.cardTypeImageView)

        val editTextNumber = findViewById<EditText>(R.id.editTextNumber)
        val editTextClientName = findViewById<EditText>(R.id.editTexClientName)
        val editTextSecurityCode = findViewById<EditText>(R.id.editTextSecurityCode)
        val editTextExpirationDate = findViewById<EditText>(R.id.editTextExpirationDate)

        val dropdownMenuCardType = findViewById<Spinner>(R.id.dropdown_menu_card_type)
        val dropdownMenuBancks = findViewById<Spinner>(R.id.dropdown_menu_banck)

        val buttonCreateCard = findViewById<Button>(R.id.buttonCreateCard)

        getDropdownMenuCardType(dropdownMenuCardType)
        getDropdownMenuBancks(dropdownMenuBancks)
        getListeners(
            cardNumberTextView,
            cardClientNameTextView,
            cardExpiryDateTextView,
            cardTypeImageView,
            editTextNumber,
            editTextClientName,
            editTextSecurityCode,
            editTextExpirationDate,
            buttonCreateCard,
        )
    }

    @RequiresApi(Build.VERSION_CODES.P)
    fun getListeners(
        cardNumberTextView: TextView,
        cardClientNameTextView: TextView,
        cardExpiryDateTextView: TextView,
        cardTypeImageView: TextView,
        editTextNumber: EditText,
        editTextClientName: EditText,
        editTextSecurityCode: EditText,
        editTextExpirationDate: EditText,
        buttonCreateCard: Button,
    ) {
        editTextNumber.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                val formattedNumber = viewModel.formatCardNumber(s.toString())
                cardNumberTextView.text = formattedNumber
                val cardNumber = s.toString().toLongOrNull()
                if (cardNumber != null) {
                    val cardBrand = CardUtils.getBrandCard(cardNumber)
                    if (cardBrand != null) {
                        cardTypeImageView.text = cardBrand.desc
                    } else {
                        cardTypeImageView.text = "Desconocida"
                    }
                } else {
                    cardTypeImageView.text = ""
                }
            }
        })

        editTextClientName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                cardClientNameTextView.text = s.toString()
            }
        })

        editTextExpirationDate.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                cardExpiryDateTextView.text = s.toString()
            }
        })

        buttonCreateCard.setOnClickListener {
            viewModel.addDigitalCard(
                number = editTextNumber.text.toString().toLongOrNull() ?: 0,
                securityCode = editTextSecurityCode.text.toString().toIntOrNull() ?: 0,
                expirationDate = editTextExpirationDate.text.toString().toLongOrNull() ?: 0
            )

            Toast.makeText(this, "Tarjeta creada", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun getDropdownMenuCardType(dropdownMenuCardType: Spinner) {
        val typeCard = CardType.entries.toTypedArray()
        val cardTypeAdapter =
            ArrayAdapter(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                typeCard.map { it.desc })
        dropdownMenuCardType.adapter = cardTypeAdapter


        dropdownMenuCardType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedCardType = typeCard[position]
                viewModel.onCardTypeSelected(selectedCardType.code)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun getDropdownMenuBancks(dropdownMenuBancks: Spinner) {
        val bankItems = Bank.entries.toTypedArray()
        val bankAdapter =
            ArrayAdapter(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                bankItems.map { it.desc })
        dropdownMenuBancks.adapter = bankAdapter
        dropdownMenuBancks.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedBank = bankItems[position]
                viewModel.onBankSelected(selectedBank.code)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }
}
