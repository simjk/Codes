package e.j_enn.repa.control;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.text.DecimalFormat;
import java.util.List;

import e.j_enn.repa.R;
import e.j_enn.repa.boundary.ViewProperty;
import e.j_enn.repa.entity.Property;

public class PropertyRecyclerAdapter extends RecyclerView.Adapter<PropertyRecyclerAdapter.PropertyViewHolder> {
    private List<Property> listProperty;

    public PropertyRecyclerAdapter(List<Property> listProperty) {
        this.listProperty = listProperty;
    }

    @Override
    public PropertyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflating recycler item view
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_user_recycler, parent, false);

        return new PropertyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PropertyViewHolder holder, final int position) {
        final Bitmap bitmapImage = getImage(listProperty.get(position).getProperty_Image());
        String price = listProperty.get(position).getPrice();

        holder.imageViewProperty.setImageBitmap(bitmapImage);
        holder.textViewAddress.setText(listProperty.get(position).getAddress());
        holder.textViewHousingUnit.setText(listProperty.get(position).getHousing_unit());
        holder.textViewTownArea.setText(listProperty.get(position).getTown_area());
        holder.textViewPropertyType.setText(listProperty.get(position).getProperty_type());

        if(!price.isEmpty()) {
            double amount = Double.parseDouble(price);
            DecimalFormat formatter = new DecimalFormat("#,###");
            holder.textViewPrice.setText("S$" + formatter.format(amount));
        }else
            holder.textViewPrice.setText("S$0");
//        holder.textViewTenure.setText(listProperty.get(position).getTenure());
//        holder.textViewFloorArea.setText(listProperty.get(position).getFloor_area());
//        holder.textViewNoOfBed.setText(listProperty.get(position).getNo_of_bed());
//        holder.textViewNoOfBath.setText(listProperty.get(position).getNo_of_bath());
//        holder.textViewDescription.setText(listProperty.get(position).getDescription());
//        holder.textViewUser.setText(listProperty.get(position).getUser());
        holder.selectedItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(v.getContext(), "You click " + position
//                        +"description: " + listProperty.get(position).getDescription()
//                        +"\nprice: " +listProperty.get(position).getPrice(), Toast.LENGTH_LONG).show();
//                List<Property> list = new ArrayList<>();
//                list.add(listProperty.get(position));
//                byte[] image = getBytes(bitmapImage);
                Bundle bundle = new Bundle();

                bundle.putString("housingUnit", listProperty.get(position).getHousing_unit());
                bundle.putString("townArea", listProperty.get(position).getTown_area());
                bundle.putString("address", listProperty.get(position).getAddress());
                bundle.putString("propertyType", listProperty.get(position).getProperty_type());
                bundle.putString("price", listProperty.get(position).getPrice());
                bundle.putString("tenure", listProperty.get(position).getTenure());
                bundle.putString("floorArea", listProperty.get(position).getFloor_area());
                bundle.putString("bed", listProperty.get(position).getNo_of_bed());
                bundle.putString("bath", listProperty.get(position).getNo_of_bath());
                bundle.putString("description", listProperty.get(position).getDescription());
                bundle.putString("user", listProperty.get(position).getUser());
                v.getContext().startActivity(new Intent(v.getContext(), ViewProperty.class).putExtras(bundle));

            }
        });
    }

    @Override
    public int getItemCount() {
        Log.v(PropertyRecyclerAdapter.class.getSimpleName(),""+listProperty.size());
        return listProperty.size();
    }


    /**
     * ViewHolder class
     */
    public class PropertyViewHolder extends RecyclerView.ViewHolder {

        public AppCompatTextView textViewHousingUnit;
        public AppCompatTextView textViewAddress;
        public AppCompatTextView textViewPropertyType;
        public AppCompatTextView textViewPrice;
        public AppCompatImageView imageViewProperty;
        public AppCompatTextView textViewTownArea;
        public AppCompatTextView textViewTenure;
        public AppCompatTextView textViewFloorArea;
        public AppCompatTextView textViewNoOfBath;
        public AppCompatTextView textViewNoOfBed;
        public AppCompatTextView textViewDescription;
        public AppCompatTextView textViewUser;
        public LinearLayout selectedItem;

        public PropertyViewHolder(View view) {
            super(view);
            imageViewProperty = (AppCompatImageView) view.findViewById(R.id.imageView2);
            textViewHousingUnit = (AppCompatTextView) view.findViewById(R.id.housingUnit);
            textViewAddress = (AppCompatTextView) view.findViewById(R.id.address);
            textViewTownArea = (AppCompatTextView) view.findViewById(R.id.townArea);
            textViewPropertyType = (AppCompatTextView) view.findViewById(R.id.propertyType);
            textViewPrice = (AppCompatTextView) view.findViewById(R.id.price);

            selectedItem = view.findViewById(R.id.selected_Item);
//            textViewTenure = (AppCompatTextView) view.findViewById(R.id.tenure_v);
//            textViewFloorArea = (AppCompatTextView) view.findViewById(R.id.floorarea);
//            textViewNoOfBed = (AppCompatTextView) view.findViewById(R.id.noOfBed);
//            textViewNoOfBath = (AppCompatTextView) view.findViewById(R.id.noOfBath);
//            textViewDescription = (AppCompatTextView) view.findViewById(R.id.descr);
//            textViewUser = (AppCompatTextView) view.findViewById(R.id.user);

        }
    }

    public static Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }
}
