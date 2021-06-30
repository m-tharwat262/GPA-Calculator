package com.example.android.explorationgpa.settings;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.android.explorationgpa.R;

public class SubjectOrderActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_subject_order);

    }



    // inner class contain all subject names (resource ids).
    private static class SubjectNames {


        // all subject in semester (1).
        public static final int MATHEMATICS_I = R.string.semester_1_subject_1;
        public static final int MECHANICS_I = R.string.semester_1_subject_2;
        public static final int PHYSICS_I = R.string.semester_1_subject_3;
        public static final int GENERAL_CHEMISTRY = R.string.semester_1_subject_4;
        public static final int INTRODUCTION_TO_GEOLOGY = R.string.semester_1_subject_5;
        public static final int INTRODUCTION_TO_COMPUTER = R.string.semester_1_subject_6;
        public static final int ENGINEERING_DRAWING_AND_PROJECTION = R.string.semester_1_subject_7;


        // all subject in semester (2).
        public static final int MATHEMATICS_II = R.string.semester_2_subject_1;
        public static final int MECHANICS_II = R.string.semester_2_subject_2;
        public static final int PHYSICS_II = R.string.semester_2_subject_3;
        public static final int INTRODUCTION_TO_PRODUCTION_ENGINEERING = R.string.semester_2_subject_4;
        public static final int HISTORY_OF_ENGINEERING = R.string.semester_1_subject_1;
        public static final int TECHNICAL_ENGLISH_I = R.string.semester_2_subject_6;


        // all subject in semester (3).
        public static final int MATHEMATICS_III = R.string.semester_3_subject_1;
        public static final int PHYSICS_III = R.string.semester_3_subject_2;
        public static final int PHYSICAL_CHEMISTRY = R.string.semester_3_subject_3;
        public static final int STRENGTH_OF_MATERIALS = R.string.semester_3_subject_4;
        public static final int SEDIMENTOLOGY_AND_STRATIGRAPHY = R.string.semester_3_subject_5;
        public static final int COMPUTER_PROGRAMMING = R.string.semester_3_subject_6;
        public static final int TECHNICAL_ENGLISH_II = R.string.semester_3_subject_7;


        // all subject in semester (4).
        public static final int ORGANIC_CHEMISTRY = R.string.semester_4_subject_1;
        public static final int STRUCTURE_GEOLOGY = R.string.semester_4_subject_2;
        public static final int PLAN_SURVEYING_AND_TOPOGRAPHY = R.string.semester_4_subject_3;
        public static final int INTRODUCTION_TO_MATERIAL = R.string.semester_4_subject_4;
        public static final int INTRODUCTION_TO_PETROLEUM_ENGINEERING = R.string.semester_4_subject_5;
        public static final int ENGINEERING_DRAWING_AND_AUTO_CAD = R.string.semester_4_subject_6;
        public static final int HUMAN_RIGHTS = R.string.semester_4_subject_7;


        // all subject in semester (5).
        public static final int ANALYTICAL_CHEMISTRY = R.string.semester_5_subject_1;
        public static final int FLUID_MECHANICS = R.string.semester_5_subject_2;
        public static final int RESERVOIR_ROCK_PROPERTIES = R.string.semester_5_subject_3;
        public static final int FUNDAMENTAL_OF_ELECTRICAL_ENGINEERING = R.string.semester_5_subject_4;
        public static final int AUTOMATIC_CONTROL = R.string.semester_5_subject_5;
        public static final int GAS_TREATMENT = R.string.semester_5_subject_6;
        public static final int TECHNICAL_ENGLISH_III = R.string.semester_5_subject_7;


        // all subject in semester (6).
        public static final int DRILLING_ENGINEERING_I = R.string.semester_6_subject_1;
        public static final int ROCK_MECHANICS = R.string.semester_6_subject_2;
        public static final int THERMODYNAMICS = R.string.semester_6_subject_3;
        public static final int RESERVOIR_FLUIDS_PROPERTIES = R.string.semester_6_subject_4;
        public static final int CRUDE_OIL_EVALUATION = R.string.semester_6_subject_5;
        public static final int ECONOMICS_AND_MANAGEMENT = R.string.semester_6_subject_6;


        // all subject in semester (7).
        public static final int EXPLORATION_GEOPHYSICS_I = R.string.semester_7_subject_1;
        public static final int HORIZONTAL_DRILLING_TECHNOLOGY = R.string.semester_7_subject_2;
        public static final int PETROLEUM_GEOLOGY = R.string.semester_7_subject_3;
        public static final int WELL_LOGGING = R.string.semester_7_subject_4;
        public static final int NATURAL_GAS_ENGINEERING = R.string.semester_7_subject_5;
        public static final int SAFETY_AND_LABORS_LAW = R.string.semester_7_subject_6;


        // all subject in semester (8).
        public static final int EXPLORATION_GEOPHYSICS_II = R.string.semester_8_subject_1;
        public static final int PETROLEUM_PRODUCTION_ENGINEERING_I = R.string.semester_8_subject_2;
        public static final int DRILLING_AND_PRODUCTION_EQUIPMENTS = R.string.semester_8_subject_3;
        public static final int APPLIED_RESERVOIR_ENGINEERING = R.string.semester_8_subject_4;
        public static final int WELL_COMPLETION_ENGINEERING = R.string.semester_8_subject_5;
        public static final int ENVIRONMENTAL_RISK_ASSESSMENT_IN_PETROLEUM_INDUSTRY = R.string.semester_8_subject_6;


        // all subject in semester (9).
        public static final int PETROLEUM_PRODUCTION_ENGINEERING_II = R.string.semester_9_subject_1;
        public static final int DRILLING_ENGINEERING_II = R.string.semester_9_subject_2;
        public static final int WELL_TESTING = R.string.semester_9_subject_3;
        public static final int COMPUTER_APPLICATION_IN_PETROLEUM_ENGINEER = R.string.semester_9_subject_4;
        public static final int ENHANCED_OIL_RECOVERY = R.string.semester_9_subject_5;
        public static final int ENGINEERING_ESTHETES = R.string.semester_9_subject_6;
        public static final int SENIOR_PROJECT_I = R.string.semester_9_subject_7;


        // all subject in semester (10).
        public static final int WELL_DESIGN = R.string.semester_10_subject_1;
        public static final int FORMATION_EVALUATION = R.string.semester_10_subject_2;
        public static final int TRANSPORTING_AND_STORAGE_OF_OIL_AND_GAS = R.string.semester_10_subject_3;
        public static final int RESERVOIR_SIMULATION = R.string.semester_10_subject_4;
        public static final int FORMATION_STIMULATION = R.string.semester_10_subject_5;
        public static final int MANAGEMENT_SCIENCE_AND_DETERMINATION_DECISION_MODELS = R.string.semester_10_subject_6;
        public static final int SENIOR_PROJECT_II = R.string.semester_10_subject_7;


        // (others subject).
        public static final int CORROSION = R.string.semester_0_subject_1;


    }



}
