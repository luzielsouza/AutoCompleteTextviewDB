package com.example.autocompletetextviewdb;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.AutoCompleteTextView;

public class CustomAutoCompleteView extends AutoCompleteTextView {

	public CustomAutoCompleteView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public CustomAutoCompleteView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public CustomAutoCompleteView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	// como desativar o filtro do AutoCompleteTextView
	@Override
	protected void performFiltering(final CharSequence text, final int keyCode) {
		String filterText = "";
		super.performFiltering(filterText, keyCode);
	}

	/*
	 * depois de uma seleção nos temos que capturar um novo valor e anexar ao texto existente
	 */
	@Override
	protected void replaceText(final CharSequence text) {
		super.replaceText(text);
	}

}