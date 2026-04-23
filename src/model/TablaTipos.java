package model;

/**
 * Clase auxiliar para gestionar las efectividades de los tipos y las reglas 
 * Fuerte: x1.5 - Débil: x0.5 - Inmune: x0.0 - Neutro: x1.0
 */
public class TablaTipos {

    public static double obtenerEficacia(Tipos ataque, Tipos defensa) {
        if (defensa == null) return 1.0;

        switch (ataque) {
        case AGUA:
            if (defensa == Tipos.FUEGO || defensa == Tipos.ROCA || defensa == Tipos.TIERRA) {
            	return 2.0; 
            }
            if (defensa == Tipos.AGUA || defensa == Tipos.DRAGÓN || defensa == Tipos.PLANTA) {
            	return 0.5;
            }
            break;

        case BICHO:
            if (defensa == Tipos.PLANTA || defensa == Tipos.PSÍQUICO || defensa == Tipos.SINIESTRO) {
            	return 2.0;
            }
            if (defensa == Tipos.FUEGO || defensa == Tipos.LUCHA || defensa == Tipos.VENENO || defensa == Tipos.VOLADOR || defensa == Tipos.FANTASMA) {
            	return 0.5;
            }
            break;

        case DRAGÓN:
            if (defensa == Tipos.DRAGÓN) {
            	return 2.0;
            }
            if (defensa == Tipos.ACERO) {
            	return 0.5;
            }
            if (defensa == Tipos.HADA) {
            	return 0.0;
            }
            break;

        case ELÉCTRICO:
            if (defensa == Tipos.AGUA || defensa == Tipos.VOLADOR) {
            	return 2.0;
            }
            if (defensa == Tipos.ELÉCTRICO || defensa == Tipos.PLANTA || defensa == Tipos.DRAGÓN) {
            	return 0.5;
            }
            if (defensa == Tipos.TIERRA) {
            	return 0.0;
            }
            break;

        case FANTASMA:
            if (defensa == Tipos.FANTASMA || defensa == Tipos.PSÍQUICO) {
            	return 2.0;
            }
            if (defensa == Tipos.SINIESTRO) {
            	return 0.5;
            }
            if (defensa == Tipos.NORMAL) {
            	return 0.0;
            }
            break;

        case FUEGO:
            if (defensa == Tipos.BICHO || defensa == Tipos.HIELO || defensa == Tipos.PLANTA || defensa == Tipos.ACERO) {
            	return 2.0;
            }
            if (defensa == Tipos.AGUA || defensa == Tipos.DRAGÓN || defensa == Tipos.FUEGO || defensa == Tipos.ROCA) {
            	return 0.5;
            }
            break;

        case HIELO:
            if (defensa == Tipos.DRAGÓN || defensa == Tipos.PLANTA || defensa == Tipos.TIERRA || defensa == Tipos.VOLADOR) {
            	return 2.0;
            }
            if (defensa == Tipos.AGUA || defensa == Tipos.FUEGO || defensa == Tipos.HIELO || defensa == Tipos.ACERO) {
            	return 0.5;
            }
            break;

        case LUCHA:
            if (defensa == Tipos.HIELO || defensa == Tipos.NORMAL || defensa == Tipos.ROCA || defensa == Tipos.SINIESTRO || defensa == Tipos.ACERO) {
            	return 2.0;
            }
            if (defensa == Tipos.BICHO || defensa == Tipos.PSÍQUICO || defensa == Tipos.VENENO || defensa == Tipos.VOLADOR || defensa == Tipos.HADA) {
            	return 0.5;
            }
            if (defensa == Tipos.FANTASMA) {
            	return 0.0;
            }
            break;

        case NORMAL:
            if (defensa == Tipos.ROCA || defensa == Tipos.ACERO) {
            	return 0.5;
            }
            if (defensa == Tipos.FANTASMA) {
            	return 0.0;
            }
            break;

        case PLANTA:
            if (defensa == Tipos.AGUA || defensa == Tipos.ROCA || defensa == Tipos.TIERRA) {
            	return 2.0;
            }
            if (defensa == Tipos.BICHO || defensa == Tipos.DRAGÓN || defensa == Tipos.FUEGO || defensa == Tipos.PLANTA || defensa == Tipos.VENENO || defensa == Tipos.VOLADOR || defensa == Tipos.ACERO) {
            	return 0.5;
            }
            break;

        case PSÍQUICO:
            if (defensa == Tipos.LUCHA || defensa == Tipos.VENENO) {
            	return 2.0;
            }
            if (defensa == Tipos.PSÍQUICO || defensa == Tipos.ACERO) {
            	return 0.5;
            }
            if (defensa == Tipos.SINIESTRO) {
            	return 0.0;
            }
            break;

        case ROCA:
            if (defensa == Tipos.BICHO || defensa == Tipos.FUEGO || defensa == Tipos.HIELO || defensa == Tipos.VOLADOR) {
            	return 2.0;
            }
            if (defensa == Tipos.LUCHA || defensa == Tipos.TIERRA || defensa == Tipos.ACERO) {
            	return 0.5;
            }
            break;

        case SINIESTRO:
            if (defensa == Tipos.FANTASMA || defensa == Tipos.PSÍQUICO) {
            	return 2.0;
            }
            if (defensa == Tipos.LUCHA || defensa == Tipos.SINIESTRO || defensa == Tipos.HADA) {
            	return 0.5;
            }
            break;

        case TIERRA:
            if (defensa == Tipos.ELÉCTRICO || defensa == Tipos.FUEGO || defensa == Tipos.ROCA || defensa == Tipos.VENENO || defensa == Tipos.ACERO) {
            	return 2.0;
            }
            if (defensa == Tipos.BICHO || defensa == Tipos.PLANTA) {
            	return 0.5;
            }
            if (defensa == Tipos.VOLADOR) {
            	return 0.0;
            }
            break;

        case VENENO:
            if (defensa == Tipos.HADA || defensa == Tipos.PLANTA) {
            	return 2.0;
            }
            if (defensa == Tipos.VENENO || defensa == Tipos.TIERRA || defensa == Tipos.ROCA || defensa == Tipos.FANTASMA) {
            	return 0.5;
            }
            if (defensa == Tipos.ACERO) {
            	return 0.0;
            }
            break;

        case VOLADOR:
            if (defensa == Tipos.BICHO || defensa == Tipos.LUCHA || defensa == Tipos.PLANTA) {
            	return 2.0;
            }
            if (defensa == Tipos.ELÉCTRICO || defensa == Tipos.ROCA || defensa == Tipos.ACERO) {
            	return 0.5;
            }
            break;

        case ACERO:
            if (defensa == Tipos.HADA || defensa == Tipos.HIELO || defensa == Tipos.ROCA) {
            	return 2.0;
            }
            if (defensa == Tipos.ACERO || defensa == Tipos.AGUA || defensa == Tipos.ELÉCTRICO || defensa == Tipos.FUEGO) {
            	return 0.5;
            }
            break;

        case HADA:
            if (defensa == Tipos.LUCHA || defensa == Tipos.DRAGÓN || defensa == Tipos.SINIESTRO) {
            	return 2.0;
            }
            if (defensa == Tipos.FUEGO || defensa == Tipos.VENENO || defensa == Tipos.ACERO) {
            	return 0.5;
            }
            break;

        default:
            return 1.0;
    }
    return 1.0;
}
}