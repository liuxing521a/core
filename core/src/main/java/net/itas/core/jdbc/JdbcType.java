package net.itas.core.jdbc;

import java.util.Objects;

import org.itas.util.ItasException;
import org.itas.util.Utils.ClassUtils;

import net.itas.core.annotation.Size;

public enum JdbcType {
	
	BYTE {
		@Override
		Class<?> clazz() {
			return byte.class;
		}

		@Override
		String suffix() {
			return "`%s` TINYINT(1) NOT NULL DEFAULT '0'";
		}
	},
	SHORT {
		@Override
		Class<?> clazz() {
			return short.class;
		}

		@Override
		String suffix() {
			return "`%s` SMALLINT(5) NOT NULL DEFAULT '0'";
		}
	},
	INT {
		@Override
		Class<?> clazz() {
			return int.class;
		}

		@Override
		String suffix() {
			return "`%s` INT(10) NOT NULL DEFAULT '0'";
		}
	},
	LONG {
		@Override
		Class<?> clazz() {
			return long.class;
		}

		@Override
		String suffix() {
			return "`%s` INT(18) NOT NULL DEFAULT '0'";
		}
	},
	FLOAT {
		@Override
		Class<?> clazz() {
			return float.class;
		}

		@Override
		String suffix() {
			return "`%s` FLOAT(8, 2) NOT NULL DEFAULT '0.0'";
		}
	},
	DOUBLE {
		@Override
		Class<?> clazz() {
			return double.class;
		}

		@Override
		String suffix() {
			return "`%s` FLOAT(14, 4) NOT NULL DEFAULT '0.0'";
		}
	},
	CHAR {
		@Override
		Class<?> clazz() {
			return char.class;
		}

		@Override
		String suffix() {
			return "`%s` CHAR(1) NOT NULL DEFAULT ' '";
		}
	},
	BOOL {
		@Override
		Class<?> clazz() {
			return boolean.class;
		}

		@Override
		String suffix() {
			return "`%s` TINYINT(1) NOT NULL DEFAULT '0'";
		}
	},
	STRING {
		@Override
		Class<?> clazz() {
			return String.class;
		}

		@Override
		String suffix() {
			return "`%s` VARCHAR(%s) NOT NULL DEFAULT ''";
		}
	},
	
	TIMESTAMP {
		@Override
		Class<?> clazz() {
			return java.sql.Timestamp.class;
		}

		@Override
		String suffix() {
			return "`%s` TIMESTAMP NOT NULL";
		}
	},
	SIMPLE {
		@Override
		Class<?> clazz() {
			return org.itas.core.Simple.class;
		}

		@Override
		String suffix() {
			return "`%s` VARCHAR(36) NOT NULL  DEFAULT ''";
		}
	},
	LIST {
		@Override
		Class<?> clazz() {
			return java.util.List.class;
		}

		@Override
		String suffix() {
			return "`%s` TEXT";
		}
	},
	SET {
		@Override
		Class<?> clazz() {
			return java.util.Set.class;
		}

		@Override
		String suffix() {
			return "`%s` TEXT";
		}
	},
	MAP {
		@Override
		Class<?> clazz() {
			return java.util.Map.class;
		}

		@Override
		String suffix() {
			return "`%s` TEXT";
		}
	},
	XML {
		@Override
		Class<?> clazz() {
			return org.itas.core.resource.Resource.class;
		}

		@Override
		String suffix() {
			return "`%s` VARCHAR(16) NOT NULL DEFAULT ''";
		}
	},
	EBYTE {
		@Override
		Class<?> clazz() {
			return org.itas.core.enums.EByte.class;
		}

		@Override
		String suffix() {
			return "`%s` TINYINT(1) NOT NULL DEFAULT '0'";
		}
	},
	EINT {
		@Override
		Class<?> clazz() {
			return org.itas.core.enums.EInt.class;
		}

		@Override
		String suffix() {
			return "`%s` INT(10) NOT NULL DEFAULT '0'";
		}
	},
	ESTRING {
		@Override
		Class<?> clazz() {
			return org.itas.core.enums.EString.class;
		}

		@Override
		String suffix() {
			return "`%s` VARCHAR(16) NOT NULL DEFAULT ''";
		}
	},
	
	;

	private static java.util.Map<Class<?>, JdbcType> typeMap;
	
	static {
		JdbcType[] types = JdbcType.values();
		typeMap = new java.util.HashMap<>(types.length);
		
		for (JdbcType type : types) {
			typeMap.put(type.clazz(), type);
		}
	}
	
	private JdbcType() {
	}
	
	abstract Class<?> clazz();
	
	abstract String suffix();
	
	public static String columnSQL(javassist.CtField field, Size size) throws Exception {
		Class<?> clazz = field.getType().toClass();
		
		JdbcType column = typeMap.get(clazz);
		if (Objects.isNull(column)) {
			throw new ItasException("unsupped class type:" + clazz.getName());
		} else if (clazz == String.class) {
			return String.format(column.suffix(), field.getName(), Objects.nonNull(size) ? size.value() : 24);
		} else if (ClassUtils.isExtends(clazz, java.util.List.class)) {
			return String.format(column.suffix(), field.getName());
		} else if (ClassUtils.isExtends(clazz, java.util.Set.class)) {
			return String.format(column.suffix(), field.getName());
		} else if (ClassUtils.isExtends(clazz, java.util.Map.class)) {
			return String.format(column.suffix(), field.getName());
		} else if (ClassUtils.isExtends(clazz, org.itas.core.resource.Resource.class)) {
			return String.format(column.suffix(), field.getName());
		} else if (clazz == org.itas.core.Simple.class) {
			return String.format(column.suffix(), field.getName());
		} else if (ClassUtils.isExtends(clazz, org.itas.core.enums.EByte.class)) {
			return String.format(column.suffix(), field.getName());
		} else if (ClassUtils.isExtends(clazz, org.itas.core.enums.EInt.class)) { 
			return String.format(column.suffix(), field.getName());
		} else if (ClassUtils.isExtends(clazz, org.itas.core.enums.EString.class)) {
			return String.format(column.suffix(), field.getName());
		} else {
			return String.format(column.suffix(), field.getName());
		}
	}
}
