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
 * DAO for table "TRANSLATE".
 */
public class TranslateDao extends AbstractDao<Translate, String> {

	public static final String TABLENAME = "TRANSLATE";

	public TranslateDao(DaoConfig config) {
		super(config);
	}


	public TranslateDao(DaoConfig config, DaoSession daoSession) {
		super(config, daoSession);
	}

	/**
	 * Creates the underlying database table.
	 */
	public static void createTable(Database db, boolean ifNotExists) {
		String constraint = ifNotExists ? "IF NOT EXISTS " : "";
		db.execSQL("CREATE TABLE " + constraint + "\"TRANSLATE\" (" + //
				"\"LANG\" TEXT NOT NULL ," + // 0: Lang
				"\"TRANSLATED_TEXT\" TEXT PRIMARY KEY NOT NULL ," + // 1: TranslatedText
				"\"IS_BOOKMARK\" INTEGER);"); // 2: isBookmark
	}

	/**
	 * Drops the underlying database table.
	 */
	public static void dropTable(Database db, boolean ifExists) {
		String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"TRANSLATE\"";
		db.execSQL(sql);
	}

	@Override
	protected final void bindValues(DatabaseStatement stmt, Translate entity) {
		stmt.clearBindings();
		stmt.bindString(1, entity.getLang());

		String TranslatedText = entity.getTranslatedText();
		if (TranslatedText != null) {
			stmt.bindString(2, TranslatedText);
		}

		Boolean isBookmark = entity.getIsBookmark();
		if (isBookmark != null) {
			stmt.bindLong(3, isBookmark ? 1L : 0L);
		}
	}

	@Override
	protected final void bindValues(SQLiteStatement stmt, Translate entity) {
		stmt.clearBindings();
		stmt.bindString(1, entity.getLang());

		String TranslatedText = entity.getTranslatedText();
		if (TranslatedText != null) {
			stmt.bindString(2, TranslatedText);
		}

		Boolean isBookmark = entity.getIsBookmark();
		if (isBookmark != null) {
			stmt.bindLong(3, isBookmark ? 1L : 0L);
		}
	}

	@Override
	public String readKey(Cursor cursor, int offset) {
		return cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1);
	}

	@Override
	public Translate readEntity(Cursor cursor, int offset) {
		return new Translate( //
				cursor.getString(offset), // Lang
				cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // TranslatedText
				cursor.isNull(offset + 2) ? null : cursor.getShort(offset + 2) != 0 // isBookmark
		);
	}

	@Override
	public void readEntity(Cursor cursor, Translate entity, int offset) {
		entity.setLang(cursor.getString(offset));
		entity.setTranslatedText(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
		entity.setIsBookmark(cursor.isNull(offset + 2) ? null : cursor.getShort(offset + 2) != 0);
	}

	@Override
	protected final String updateKeyAfterInsert(Translate entity, long rowId) {
		return entity.getTranslatedText();
	}

	@Override
	public String getKey(Translate entity) {
		if (entity != null) {
			return entity.getTranslatedText();
		} else {
			return null;
		}
	}

	@Override
	public boolean hasKey(Translate entity) {
		return entity.getTranslatedText() != null;
	}

	@Override
	protected final boolean isEntityUpdateable() {
		return true;
	}

	/**
	 * Properties of entity Translate.<br/>
	 * Can be used for QueryBuilder and for referencing column names.
	 */
	public static class Properties {
		public final static Property Lang = new Property(0, String.class, "Lang", false, "LANG");
		public final static Property TranslatedText = new Property(1, String.class, "TranslatedText", true, "TRANSLATED_TEXT");
		public final static Property IsBookmark = new Property(2, Boolean.class, "isBookmark", false, "IS_BOOKMARK");
	}

}