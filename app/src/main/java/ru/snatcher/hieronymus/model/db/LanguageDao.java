package ru.snatcher.hieronymus.model.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;
import org.greenrobot.greendao.internal.DaoConfig;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * DAO for table "LANGUAGE".
 */
public class LanguageDao extends AbstractDao<Language, String> {

	public static final String TABLENAME = "LANGUAGE";

	public LanguageDao(DaoConfig config) {
		super(config);
	}


	public LanguageDao(DaoConfig config, DaoSession daoSession) {
		super(config, daoSession);
	}

	/**
	 * Creates the underlying database table.
	 */
	public static void createTable(Database db, boolean ifNotExists) {
		String constraint = ifNotExists ? "IF NOT EXISTS " : "";
		db.execSQL("CREATE TABLE " + constraint + "\"LANGUAGE\" (" + //
				"\"LANG_KEY\" TEXT PRIMARY KEY NOT NULL ," + // 0: LangKey
				"\"LANG_VALUE\" TEXT NOT NULL );"); // 1: LangValue
	}

	/**
	 * Drops the underlying database table.
	 */
	public static void dropTable(Database db, boolean ifExists) {
		String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"LANGUAGE\"";
		db.execSQL(sql);
	}

	@Override
	protected final void bindValues(DatabaseStatement stmt, Language entity) {
		stmt.clearBindings();

		String LangKey = entity.getLangKey();
		if (LangKey != null) {
			stmt.bindString(1, LangKey);
		}
		stmt.bindString(2, entity.getLangValue());
	}

	@Override
	protected final void bindValues(SQLiteStatement stmt, Language entity) {
		stmt.clearBindings();

		String LangKey = entity.getLangKey();
		if (LangKey != null) {
			stmt.bindString(1, LangKey);
		}
		stmt.bindString(2, entity.getLangValue());
	}

	@Override
	public String readKey(Cursor cursor, int offset) {
		return cursor.isNull(offset) ? null : cursor.getString(offset);
	}

	@Override
	public Language readEntity(Cursor cursor, int offset) {
		return new Language( //
				cursor.isNull(offset) ? null : cursor.getString(offset), // LangKey
				cursor.getString(offset + 1) // LangValue
		);
	}

	@Override
	public void readEntity(Cursor cursor, Language entity, int offset) {
		entity.setLangKey(cursor.isNull(offset) ? null : cursor.getString(offset));
		entity.setLangValue(cursor.getString(offset + 1));
	}

	@Override
	protected final String updateKeyAfterInsert(Language entity, long rowId) {
		return entity.getLangKey();
	}

	@Override
	public String getKey(Language entity) {
		if (entity != null) {
			return entity.getLangKey();
		} else {
			return null;
		}
	}

	@Override
	public boolean hasKey(Language entity) {
		return entity.getLangKey() != null;
	}

	@Override
	protected final boolean isEntityUpdateable() {
		return true;
	}

	/**
	 * Properties of entity Language.<br/>
	 * Can be used for QueryBuilder and for referencing column names.
	 */
	public static class Properties {
		public final static Property LangKey = new Property(0, String.class, "LangKey", true, "LANG_KEY");
		public final static Property LangValue = new Property(1, String.class, "LangValue", false, "LANG_VALUE");
	}

}
