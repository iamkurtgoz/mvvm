package fr.ganfra.lib.mvvm;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;


public abstract class ActivityViewModel<VM extends ViewModel> extends AppCompatActivity {

    protected VM mViewModel;

    protected abstract VM createViewModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mViewModel == null) {
            mViewModel = createViewModel();
            if (mViewModel instanceof ISavedData && savedInstanceState != null) {
                final ISavedData savedViewModel = (ISavedData) mViewModel;
                final Parcelable data = (Parcelable) savedInstanceState.get(ISavedData.EXTRA_VIEW_MODEL_DATA);
                savedViewModel.restoreData(data);
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mViewModel instanceof ISavedData) {
            final ISavedData savedViewModel = (ISavedData) mViewModel;
            final Parcelable data = savedViewModel.getDataToSave();
            outState.putParcelable(ISavedData.EXTRA_VIEW_MODEL_DATA, data);
        }
    }


    @Override
    protected void onPause() {
        mViewModel.onPause();
        super.onPause();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mViewModel.onStart();
    }

    @Override
    protected void onStop() {
        mViewModel.onStop();
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mViewModel.onResume();
    }

    @Override
    protected void onDestroy() {
        mViewModel.onDestroy();
        super.onDestroy();
    }
}
