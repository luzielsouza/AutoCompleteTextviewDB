package com.example.autocompletetextviewdb;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHandler extends SQLiteOpenHelper {

	// para seus logs
	public static final String TAG = "DatabaseHandler.java";

	// versão do bando de dados
	private static final int DATABASE_VERSION = 4;

	// nome do bando de dados
	protected static final String DATABASE_NAME = "NinjaDatabase2";

	// detalhes da tabela
	public String tableName = "locations";
	public String fieldObjectId = "id";
	public String fieldObjectName = "name";

	// construtor
	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// criando tabelas
	@Override
	public void onCreate(SQLiteDatabase db) {

		String sql = "";

		sql += "CREATE TABLE " + tableName;
		sql += " ( ";
		sql += fieldObjectId + " INTEGER PRIMARY KEY AUTOINCREMENT, ";
		sql += fieldObjectName + " TEXT ";
		sql += " ) ";

		db.execSQL(sql);

	}

	/*
	 * quando o banco de dados é atualizado, isso fará com que ele mostre o que foi criado e vai atualizar.
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		String sql = "DROP TABLE IF EXISTS " + tableName;
		db.execSQL(sql);

		onCreate(db);
	}

	/*
	 * criando nova gravação
	 * 
	 * @param myObj contem detalhes a serem adicionados em uma unica linha.
	 */
	public boolean create(MyObject myObj) {

		boolean createSuccessful = false;

		if (!checkIfExists(myObj.objectName)) {

			SQLiteDatabase db = this.getWritableDatabase();

			ContentValues values = new ContentValues();
			values.put(fieldObjectName, myObj.objectName);
			createSuccessful = db.insert(tableName, null, values) > 0;

			db.close();

			if (createSuccessful) {
				Log.e(TAG, myObj.objectName + " created.");
			}
		}

		return createSuccessful;
	}

	// verifica se ja existe um registro para que possa ser add na proxima vez que for executada
	public boolean checkIfExists(String objectName) {

		boolean recordExists = false;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery("SELECT " + fieldObjectId + " FROM "
				+ tableName + " WHERE " + fieldObjectName + " = '" + objectName
				+ "'", null);

		if (cursor != null) {

			if (cursor.getCount() > 0) {
				recordExists = true;
			}
		}

		cursor.close();
		db.close();

		return recordExists;
	}

	/*
	 * ler os registros relacionados com o termo de pesquisa
	 */
	public List<MyObject> read(String searchTerm) {

		List<MyObject> recordsList = new ArrayList<MyObject>();

		// seleciona a query
		String sql = "";
		sql += "SELECT * FROM " + tableName;
		sql += " WHERE " + fieldObjectName + " LIKE '%" + searchTerm + "%'";
		sql += " ORDER BY " + fieldObjectId + " DESC";
		sql += " LIMIT 0,5";

		SQLiteDatabase db = this.getWritableDatabase();

		// executa a query
		Cursor cursor = db.rawQuery(sql, null);

		// percorre todas as listas e add
		if (cursor.moveToFirst()) {
			do {

				// int productId =
				// Integer.parseInt(cursor.getString(cursor.getColumnIndex(fieldProductId)));
				String objectName = cursor.getString(cursor
						.getColumnIndex(fieldObjectName));
				MyObject myObject = new MyObject(objectName);

				// add para a lista
				recordsList.add(myObject);

			} while (cursor.moveToNext());
		}

		cursor.close();
		db.close();

		// retorna à lista de registros
		return recordsList;
	}

}
