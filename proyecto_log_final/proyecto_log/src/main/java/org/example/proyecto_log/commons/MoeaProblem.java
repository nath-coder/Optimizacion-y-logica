package org.example.proyecto_log.commons;

import org.example.proyecto_log.model.dto.RequireDTO;
import org.moeaframework.core.Solution;
import org.moeaframework.core.variable.EncodingUtils;
import org.moeaframework.core.variable.Permutation;
import org.moeaframework.problem.AbstractProblem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MoeaProblem  extends AbstractProblem {
    private Double totalPrecio;
    private Integer lugaresNoPosibles;

    private HashMap<Integer, ArrayList<Destino>> map;
    private Map<String,List<RequireDTO>> requires;
    private Map<String,Double> truckLoading;
    public MoeaProblem(int numberOfVariables, int numberOfObjectives,HashMap<Integer, ArrayList<Destino>> map,Map<String,List<RequireDTO>> requires,Map<String,Double> truckLoading) {
        super(numberOfVariables, numberOfObjectives);
        this.map=map;
        this.requires=requires;
        this.truckLoading=truckLoading;
    }

    @Override
    public void evaluate(Solution solution) {
        Permutation liquidopermutation = (Permutation) solution.getVariable(0);
        Permutation solidopermutation = (Permutation) solution.getVariable(1);
        Permutation flamablepermutation = (Permutation) solution.getVariable(2);
        Permutation delicadopermutation = (Permutation) solution.getVariable(3);
        Permutation partespermutation = (Permutation) solution.getVariable(4);
        totalPrecio=0.0;
        this.lugaresNoPosibles=0;
        double totalTimeLiquido = getTotalTIme(liquidopermutation,"LÍQUIDO");
        double totalTimeSolido = getTotalTIme(solidopermutation,"SÓLIDO");;
        double totalTimeFlammable = getTotalTIme(flamablepermutation,"INFLAMABLE");
        double totalTimeDelicado = getTotalTIme(delicadopermutation,"DELICADO");;
        double totalTimePartes = getTotalTIme(partespermutation,"PARTES");;
        double max=Double.max(totalTimeLiquido,totalTimeSolido);
        max=Double.max(max,totalTimeFlammable);
        max=Double.max(max,totalTimeDelicado);
        max=Double.max(max,totalTimePartes);

        //Establecer los objetivos
        solution.setObjective(0, max);
        solution.setObjective(1, totalPrecio);
        solution.setObjective(2,lugaresNoPosibles);
    }

    private Double getTotalTIme(Permutation permutation, String tipo){

        Double totalTime=0.0;

        double tempLoading=truckLoading.get(tipo);
        //Calcular tiempo total y
        // carga total basado en la permutacion de las ciudades
        Destino destinoLLegada=new Destino(0L);
        for (int i = 0; i < permutation.size() - 1; i++) {
            int requireA = permutation.get(i);
            Long cityA=requires.get(tipo).get(requireA).getIdStopDeparture();
            Long cityB=requires.get(tipo).get(requireA).getIdStopArrival();
            destinoLLegada.setId(cityB);
            totalTime+=getTime(Math.toIntExact(cityA),destinoLLegada);
            if(tempLoading>=requires.get(tipo).get(requireA).getLoading()){
                tempLoading-=requires.get(tipo).get(requireA).getLoading();
            }else
                lugaresNoPosibles++;
            if(i!=0) {
                destinoLLegada.setId(cityA);
                totalTime += getTime(Math.toIntExact(cityB), destinoLLegada);

            }
        }
        return totalTime;
    }
    private double getTime(Integer cityA,Destino destinoLlegada){
        double totalTime=0.0;
        if (map.containsKey(cityA) && map.get(cityA).contains(destinoLlegada)) {
            Destino destino = map.get(cityA).stream().filter(des->des.getId()==destinoLlegada.getId()).findFirst().get();
            totalTime += destino.getDuration();
            totalPrecio+=destino.getPrecio();
        } else {
               lugaresNoPosibles++;
        }
        return totalTime;
    }
    @Override
    public Solution newSolution() {
        Solution solution = new Solution(this.numberOfVariables, this.getNumberOfObjectives());
        Permutation permutationSolido = EncodingUtils.newPermutation(requires.get("SÓLIDO").size());
        Permutation permutationLiquido = EncodingUtils.newPermutation(requires.get("LÍQUIDO").size());
        Permutation permutationFlamable = EncodingUtils.newPermutation(requires.get("INFLAMABLE").size());
        Permutation permutationDelicado = EncodingUtils.newPermutation(requires.get("DELICADO").size());
        Permutation permutationPartes = EncodingUtils.newPermutation(requires.get("PARTES").size());

        permutationDelicado.randomize();
        permutationPartes.randomize();
        permutationFlamable.randomize();
        permutationLiquido.randomize();
        permutationSolido.randomize();
        solution.setVariable(0,permutationLiquido);
        solution.setVariable(1,permutationSolido);
        solution.setVariable(2,permutationFlamable);
        solution.setVariable(3,permutationDelicado);
        solution.setVariable(4,permutationPartes);
        return solution;
    }


}
