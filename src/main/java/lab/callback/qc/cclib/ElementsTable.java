package lab.callback.qc.cclib;

public class ElementsTable
{
    private static final String[] ELEMENTS = new String[]
            {
                    "*", "H", "He", "Li", "Be", "B", "C", "N", "O","F","Ne",
                    "Na", "Mg", "Al", "Si", "P", "S", "Cl", "Ar", "K", "Ca",
                    "Sc", "Ti", "V", "Cr", "Mn", "Fe", "Co", "Ni", "Cu", "Zn",
                    "Ga", "Ge", "As", "Se", "Br","Kr", "Rb", "Sr", "Y", "Zr",
                    "Nb", "Mo", "Tc", "Ru", "Rh", "Pd", "Ag", "Cd", "In", "Sn",
                    "Sb", "Te", "I", "Xe", "Cs", "Ba"
            };


    public static String Z(int number)
    {
        return ELEMENTS[number];
    }

}
