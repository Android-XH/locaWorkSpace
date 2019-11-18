package com.example.worktools.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public abstract class ListAdapter<T> extends BaseAdapter{
	protected List<T> mList;
	protected ListView mListView;
	protected LayoutInflater inflater;
	private Context context;
	public ListAdapter(Activity context){
		this.context=context;
		this.inflater=LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		if(mList != null)
			return mList.size();
		else
			return 0;
	}

	public Context getContext(){
		return  context;
	}
	@Override
	public T getItem(int position) {
		return mList == null ? null : mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	@Override
	public View getView(int i, View view, ViewGroup viewGroup) {
		if(view==null){
			view= inflater.inflate(getLayout(),null);
		}
		return getHolderView(i,view,getItem(i));
	}
	protected abstract int getLayout();
	protected abstract View getHolderView(int position, View convertView, T t);

	public void setList(List<T> list){
		this.mList = list;
		notifyDataSetChanged();
	}
	
	public void addList(List<T> list){
		this.mList.addAll(list);
		notifyDataSetChanged();
	}
	
	public List<T> getList(){
		if(mList==null){
			mList=new ArrayList<>();
		}
		return mList;
	}
	
	public void setList(T[] list){
		List<T> arrayList = new ArrayList<T>(list.length);  
		for (T t : list) {  
			arrayList.add(t);  
		}  
		setList(arrayList);
	}
	
	public ListView getListView(){
		return mListView;
	}
	
	public void setListView(ListView listView){
		mListView = listView;
	}
}
