
const MATTERS = {
  1000001: {
    id: 1000001,
    name: "Matemáticas Básicas",
    prerequisites: []
  },
  1000004: {
    id: 1000004,
    name: "Cálculo diferencial",
    prerequisites: [1000001]
  },
  2016377: {
    id: 2016377,
    name: "Cálculo diferencial en una variable",
    prerequisites: [1000001]
  },
  1000005: {
    id: 1000005,
    name: "Cálculo integral",
    prerequisites: [[1000004,2016377]]
  },
  2015556: {
    id: 2015556,
    name: "Cálculo integral en una variable",
    prerequisites: [[1000004,2016377]]
  },
  1000006: {
    id: 1000006,
    name: "Cálculo en Varias Variables",
    prerequisites: [[1000005,2015556]]
  },
  2015162: {
    id: 2015162,
    name: "Cálculo Vectorial",
    prerequisites: [[1000005,2015556]]
  },
  1000003: {
    id: 1000003,
    name: "Álgebra Lineal",
    prerequisites: [[1000004,2016377]]
  },    
  2015555: {
    id: 2015555,
    name: "Álgebra Lineal Básica",
    prerequisites: [[1000004,2016377]]
  },
  1000013: {
    id: 1000013,
    name: "Probabilidad y Estadística Fundamental",
    prerequisites: [[1000005,2015556]]
  },
  2027877: {
    id: 2027877,
    name: "Probabilidad Fundamental",
    prerequisites: [[1000005,2015556]]
  }, 
  2015178: {
    id: 2015178,
    name: "Probabilidad",
    prerequisites: [[1000005,2015556]]
  },  
  1000019: {
    id: 1000019,
    name: "Fundamentos de Mecánica",
    prerequisites: [[1000004,2016377]]
  }, 
  1000017: {
    id: 1000017,
    name: "Fundamentos de Electricidad y Magnetismo",
    prerequisites: [[1000005,2015556],1000019]
  },
  2015174: {
    id: 2015174,
    name: "Introducción a la Teoría de la Computación",
    prerequisites: [[2015184,2025963]]
  },
  2016696: {
    id: 2016696,
    name: "Algoritmos",
    prerequisites: [2016699,[2015184,2025963],[2025964,2026519]]
  },
  2025963: {
    id: 2025963,
    name: "Matemáticas Discretas I",
    prerequisites: [[1000003,2015555]]
  }, 
  2025819: {
    id: 2025819,
    name: "Sistemas numéricos",
    prerequisites: [1000019]
  }, 
  2025964: {
    id: 2025964,
    name: "Matemáticas Discretas II",
    prerequisites: [2025963,2015184]
  }, 
  2015181: {
    id: 2015181,
    name: "Introducción a la teoría de conjuntos",
    prerequisites: [2025819]
  },
  2015970: {
    id: 2015970,
    name: "Métodos Numéricos",
    prerequisites: [[1000006,2015162]]
  }, 
  2019072: {
    id: 2019072,
    name: "Análisis Numérico I",
    prerequisites: [2015155]
  },
  2015703: {
    id: 2015703,
    name: "Ingeniería Económica",
    prerequisites: [[1000005,2015556]]
  },
  2025986: {
    id: 2025986,
    name: "Ingeniería Económica y Análisis de Riesgo",
    prerequisites: [[1000005,2015556],1000013,[2027877,2015178],2016610]
  },
  2016047: {
    id: 2016047,
    name: "Modelos Económicos Computacionales",
    prerequisites: [[1000005,2015556]]
  },  
  2015702: {
    id: 2015702,
    name: "Gerencia y Gestión de Proyectos",
    prerequisites: [[2015703,2025986,2016047]]
  }, 
  2016028: {
    id: 2016028,
    name: "Diseño, Gestión y Evaluación de Proyectos",
    prerequisites: [[2015703,2025986,2016047]]
  },
  1000002: {
    id: 1000002,
    name: "Lecto-Escritura",
    prerequisites: []
  },
  2016499: {
    id: 2016499,
    name: "Electrónica Digital II",
    prerequisites: [2016498]
  },
  2016508: {
    id: 2016508,
    name: "Sistemas Embebidos",
    prerequisites: [2016499]
  },
  2016512: {
    id: 2016512,
    name: "Verifivación de Sistemas Digitales",
    prerequisites: [2016499]
  },
  2017271: {
    id: 2017271,
    name: "Principios de Dinámica",
    prerequisites: [[1000003, 2015555]]
  },
  2017287: {
    id: 2017287,
    name: "Sensores y Actuadores",
    prerequisites: [2016507]
  },
  2016493: {
    id: 2016493,
    name: "Control",
    prerequisites: [2016507]
  },
  2016770: {
    id: 2016770,
    name: "Robótica",
    prerequisites: [2017271, 2016493]
  },
  2016592: {
    id: 2016592,
    name: "Econimía general",
    prerequisites: []
  },
  2016610: {
    id: 2016610,
    name: "Sistemas de costos",
    prerequisites: [2016592]
  },
  1000009: {
    id: 1000009,
    name: "Biología General",
    prerequisites: []
  },
  1000010: {
    id: 1000010,
    name: "Biología Molecular y Celular",
    prerequisites: []
  },
  2016099: {
    id: 2016099,
    name: "Taller Forma y Estructura",
    prerequisites: []
  },
  2016069: {
    id: 2016069,
    name: "Fundamentos Tecnológicos: Color y Producción",
    prerequisites: [2016099]
  },
  2016061: {
    id: 2016061,
    name: "Bocetación e Ilustración",
    prerequisites:[2016069]
  },
  2016083: {
    id: 2016083,
    name: "Producción en Medios Digitales",
    prerequisites: [2016061]
  },
  2016091: {
    id: 2016091,
    name: "Taller de énfasis en Multimedia e Imagen Digital 1",
    prerequisites: [2016083]
  },
  2016093: {
    id: 2016093,
    name: "Taller de énfasis en animación y narrativas audiovisuales 1",
    prerequisites: [2016083]
  },
  2025987: {
    id: 2025987,
    name: "Modelos estocásticos para procesos de manufactura y sistemas de servicios",
    prerequisites: [[2025971, 2015173], [2025970, 2019082]]
  },
  2025988: {
    id: 2025988,
    name: "Taller de simulación procesos de manufactura y sistemas de servicios",
    prerequisites: [2025987]
  },
  2016375: {
    id: 2016375,
    name: "Programación Orientada a Objetos",
    prerequisites: [[2015734, 2026573]]
  },
  2016699: {
    id: 2016699,
    name: "Estructuras de Datos",
    prerequisites: [2016375]
  },
  2016701: {
    id: 2016701,
    name: "Ingeniería de Software I",
    prerequisites: [[2016353, 2027641], 2016703, 2016699]
  },
  2016702: {
    id: 2016702,
    name: "Ingeniería de Software II",
    prerequisites: [2016701, 2025967]
  },
  2016716: {
    id: 2016716,
    name: "Arquitectura de Software",
    prerequisites: [2016702]
  },
  2015734: {
    id: 2015734,
    name: "Programación de Computadores",
    prerequisites: []
  },
  2026573: {
    id: 2026573,
    name: "Introducción a las ciencias de la computación y a la programación",
    prerequisites: []
  },
  2025966: {
    id: 2025966,
    name: "Lenguajes de Programación",
    prerequisites: [2016699, 2015174]
  },
  2027642: {
    id: 2027642,
    name: "Compiladores",
    prerequisites: [2016699, 2015174]
  },
  2027628: {
    id: 2027628,
    name: "Teoría de Lenguajes Formales",
    prerequisites: [2016699, 2015174]
  },
  2016697: {
    id: 2016697,
    name: "Arquitectura de Computadores",
    prerequisites: [[2016698, 2016498]]
  },
  2016707: {
    id: 2016707,
    name: "Sistemas Operativos",
    prerequisites: [2016697]
  },
  2025967: {
    id: 2025967,
    name: "Redes de Computadores",
    prerequisites: [1000017, 2016699, 2016697]
  },
  2016722: {
    id: 2016722,
    name: "Computación Paralela y Distribuida",
    prerequisites: [2016696]
  },
  2025983: {
    id: 2025983,
    name: "Arquitectura de Infraestructura y gobierno de TICs",
    prerequisites: [2016702, 2025982]
  },
  2016698: {
    id: 2016698,
    name: "Elementos de Computadores",
    prerequisites: [2025975]
  },
  2016498: {
    id: 2016498,
    name: "Electrónica Digital I",
    prerequisites: [2016495]
  },
  2016353: {
    id: 2016353,
    name: "Bases de Datos",
    prerequisites: [2016375]
  },
  2027641: {
    id: 2027641,
    name: "Análisis de bases de datos",
    prerequisites: [2016375]
  },
  2025994: {
    id: 2025994,
    name: "Teoría de la Información y Sistemas de Comunicaciones",
    prerequisites: [[1000013, 2015178], 2025967]
  },
  2016492: {
    id: 2016492,
    name: "Comunicaciones",
    prerequisites: [2016503]
  },
  2025982: {
    id: 2025982,
    name: "Sistemas de Información",
    prerequisites: [[2016353,2027641], [2015702, 2016028], 2016703]
  },
  2016053: {
    id: 2016053,
    name: "Sistemas de Información Gerencial",
    prerequisites: [[2016353,2027641], [2015702, 2016028], 2016703]
  },
  2025972: {
    id: 2025972,
    name: "Introducción a la Criptografía y a la Seguridad de la Información",
    prerequisites: [2016696]
  },
  2027311: {
    id: 2027311,
    name: "Introducción a la criptografía y a la teoría de información",
    prerequisites: [2016696]
  },
  2027313: {
    id: 2027313,
    name: "Teoría de la codificación",
    prerequisites: [2016696]
  },
  2027310: {
    id: 2027310,
    name: "Criptografía",
    prerequisites: [2016696]
  },
  2027309: {
    id: 2027309,
    name: "Análisis forense digital",
    prerequisites: [2016696]
  },
  2025960: {
    id: 2025960,
    name: "Computación Visual",
    prerequisites: [2016696]
  },
  2027629: {
    id: 2027629,
    name: "Lógica computacional",
    prerequisites: [2016696]
  },
  2019267: {
    id: 2019267,
    name: "Teoría de la recursión",
    prerequisites: [2016696]
  },
  2025995: {
    id: 2025995,
    name: "Introducción a los Sistemas Inteligentes",
    prerequisites: [2016696]
  },
  2023251: {
    id: 2023251,
    name: "Inteligencia Artificial y Minirobots",
    prerequisites: [2016696]
  },
  2027631: {
    id: 2027631,
    name: "Introducción a la Inteligencia Artificial",
    prerequisites: [2016696]
  },
  2028837: {
    id: 2028837,
    name: "Matemáticas del aprendizaje de máquinas",
    prerequisites: [2016696]
  },
  2017290: {
    id: 2017290,
    name: "Técnicas de Inteligencia Artificial",
    prerequisites: [2016696]
  },
  2016703: {
    id: 2016703,
    name: "Pensamiento Sistémico",
    prerequisites: []
  },
  2025969: {
    id: 2025969,
    name: "Modelos Estocásticos y Simulación en Computación y Comunicaciones",
    prerequisites: [[2025971,2015173]]
  },
  2025970: {
    id: 2025970,
    name: "Modelos y Simulación",
    prerequisites: [[1000006, 2015162], [2025964, 2026519], [1000013, 2015178], 2016375]
  },
  2019082: {
    id: 2019082,
    name: "Modelos matemáticos I",
    prerequisites: [[1000006, 2015162], [2025964, 2026519], [1000013, 2015178], 2016375]
  },
  2025971: {
    id: 2025971,
    name: "Optimización",
    prerequisites: [[2025970, 2019082]]
  },
  2015173: {
    id: 2015173,
    name: "Introducción a la Optimización",
    prerequisites: [[2025970, 2019082]]
  },
  2025975: {
    id: 2025975,
    name: "Introducción a la Ingeniería de Sistemas y Computación",
    prerequisites: []
  },
  2024045: {
    id: 2024045,
    name: "Taller de Proyectos Interdisciplinarios",
    prerequisites: []
  },
  2016615: {
    id: 2016615,
    name: "Taller de Invención y Creatividad",
    prerequisites: []
  },
  2026551: {
    id: 2026551,
    name: "Creación y Gestión de Empresas",
    prerequisites: []
  },  
  2016007: {
    id: 2016007,
    name: "Fundamentos de Administración",
    prerequisites: []
  },
  2016600: {
    id: 2016600,
    name: "Gestión Tecnológica",
    prerequisites: []
  },
  2016599: {
    id: 2016599,
    name: "Gestión de la Ciencia, la Tecnología y la Innovación",
    prerequisites: []
  },
  2016741: {
    id: 2016741,
    name: "Finanzas",
    prerequisites: []
  },
  2016037: {
    id: 2016037,
    name: "Finanzas Avanzadas",
    prerequisites: [[1000005,2015556]]
  },
  2025974: {
    id: 2025974,
    name: "Trabajo de Grado - Trabajo Investigativo",
    prerequisites: []
  },
  2025973: {
    id: 2025973,
    name: "Trabajo de Grado - Práctica de Extensión",
    prerequisites: []
  },
  2016843: {
    id: 2016843,
    name: "Trabajo de Grado - Asignaturas de Posgrado",
    prerequisites: []
  },
  2016762: {
    id: 2016762,
    name: "Práctica estudiantil I",
    prerequisites: []
  },
  2016763: {
    id: 2016763,
    name: "Práctica estudiantil II",
    prerequisites: []
  },
  2016764: {
    id: 2016764,
    name: "Práctica estudiantil III",
    prerequisites: []
  },
  1000070: {
    id: 1000070,
    name: "Práctica Colombia I",
    prerequisites: []
  },
  1000071: {
    id: 1000071,
    name: "Práctica Colombia II",
    prerequisites: []
  },
  1000072: {
    id: 1000072,
    name: "Práctica Colombia III",
    prerequisites: []
  },
  2015168: {
    id: 2015168,
    name: "Fundamentos de matemáticas",
    prerequisites: []
  },
  2026548: {
    id: 2026548,
    name: "Introducción al Análisis Combinatorio",
    prerequisites: [2015168]
  },
  2026519: {
    id: 2026519,
    name: "Ecuaciones en Diferencias Finitas y Sistemas Dinámicos",
    prerequisites: [1000003,2015555,2015181]
  },
  2016366: {
    id: 2016366,
    name: "Estadística Descriptiva y Exploratoria",
    prerequisites: []
  },
  2016379: {
    id: 2016379,
    name: "Inferencia Estadística",
    prerequisites: [1000013,2015178]
  },
  2016342: {
    id: 2016342,
    name: "Cálculo de ecuaciones diferenciales ordinarias",
    prerequisites: [1000005,2015556,1000003,2015555]
  },
  2016495: {
    id: 2016495,
    name: "Electrónica Análoga I",
    prerequisites: [2016489]
  },
  2016489: {
    id: 2016489,
    name: "Circuitos Eléctricos I",
    prerequisites: [2016509,1000004,2016377]
  },
  2016509: {
    id: 2016509,
    name: "Taller de Ingeniería Electrónica",
    prerequisites: [2025975]
  },
  2016503: {
    id: 2016503,
    name: "Líneas y Antenas",
    prerequisites: [2016487]
  },
  2016487: {
    id: 2016487,
    name: "Campos Electromagnéticos",
    prerequisites: [1000006,2015162,1000017,2016506]
  },
  2016506: {
    id: 2016506,
    name: "Señales y Sistemas I",
    prerequisites: [2016489,2016342]
  },
  2015159: {
    id: 2015159,
    name: "Variable Compleja",
    prerequisites: [2016342]
  },
  2016507: {
    id: 2016507,
    name: "Señales y Sistemas II",
    prerequisites: [2015159, 2016506]
  },
  2015155: {
    id: 2015155,
    name: "Análisis Real",
    prerequisites: []
  },
  2015184: {
    id: 2015184,
    name: "Lógica Matemática",
    prerequisites: []
  },
  2016360: {
    id: 2016360,
    name: "Diseño de Experimentos",
    prerequisites: []
  }
}

export default MATTERS;