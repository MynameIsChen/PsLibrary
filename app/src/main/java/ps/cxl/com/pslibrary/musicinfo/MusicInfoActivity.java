package ps.cxl.com.pslibrary.musicinfo;

import android.content.Context;
import android.content.Intent;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.media.MediaCodec;
import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.os.Bundle;
import android.view.View;

import java.io.IOException;
import java.nio.ByteBuffer;

import butterknife.BindView;
import butterknife.ButterKnife;
import ps.cxl.com.common.util.Lg;
import ps.cxl.com.pslibrary.R;
import ps.cxl.com.pslibrary.base.BaseActivity;
import ps.cxl.com.pslibrary.widgets.baseview.TitleView;

public class MusicInfoActivity extends BaseActivity {
    @BindView(R.id.title)
    TitleView mTitle;
    private MusicInfoFragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_info);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mTitle.setTitleText("info");
        mTitle.setOnBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mFragment = (MusicInfoFragment) getFragment("mFragment");
    }

    public static void launch(Context context) {
        context.startActivity(new Intent(context, MusicInfoActivity.class));
    }

    private void setData(String path){
        ByteBuffer[] codecInputBuffers;
        ByteBuffer[] codecOutputBuffers;
        MediaExtractor extractor = new MediaExtractor();
        try {
            extractor.setDataSource(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        MediaFormat format = extractor.getTrackFormat(0);
        String mime = format.getString(MediaFormat.KEY_MIME);
        if(!mime.startsWith("audio/")){
            Lg.e("MP3RadioStreamPlayer","不是音频文件！");
            return;
        }
        //声道个数：单声道或双声道
        int channels  = format.getInteger(MediaFormat.KEY_CHANNEL_COUNT);
        //时长
        long duration = format.getLong(MediaFormat.KEY_DURATION);
        //时长
        int bitrate = format.getInteger(MediaFormat.KEY_BIT_RATE);
        MediaCodec codec = null;
        try {
            codec = MediaCodec.createDecoderByType(mime);
        } catch (IOException e) {
            e.printStackTrace();
        }
        codec.configure(format,null,null,0);
        codec.start();
        //用来存放目标文件的数据
        codecInputBuffers = codec.getInputBuffers();
        //解码后的数据
        codecOutputBuffers = codec.getOutputBuffers();
        int sampleRate = format.getInteger(MediaFormat.KEY_SAMPLE_RATE);
        //设置声道类型：AudioFormat.CHANNEL_OUT_MONO单声道，AudioFormat.CHANNEL_OUT_STEREO双声道
        int channelConfiguration = channels == 1 ? AudioFormat.CHANNEL_OUT_MONO : AudioFormat.CHANNEL_OUT_STEREO;

        AudioTrack audioTrack = new AudioTrack(
                AudioManager.STREAM_MUSIC,
                sampleRate,
                channelConfiguration,
                AudioFormat.ENCODING_PCM_16BIT,
                AudioTrack.getMinBufferSize(
                        sampleRate,
                        channelConfiguration,
                        AudioFormat.ENCODING_PCM_16BIT
                ),
                AudioTrack.MODE_STREAM
        );

//开始play，等待write发出声音
        audioTrack.play();
        extractor.selectTrack(0);//选择读取音轨

// start decoding
        final long kTimeOutUs = 10000;//超时
        MediaCodec.BufferInfo info = new MediaCodec.BufferInfo();

// 解码
        boolean sawInputEOS = false;
        boolean sawOutputEOS = false;
        int noOutputCounter = 0;
        int noOutputCounterLimit = 50;
        int bufIndexCheck = 0;
        boolean doStop = false;

        while (!sawOutputEOS && noOutputCounter < noOutputCounterLimit && !doStop) {
            //Log.i(LOG_TAG, "loop ");
            noOutputCounter++;
            if (!sawInputEOS) {

                int inputBufIndex = codec.dequeueInputBuffer(kTimeOutUs);
                bufIndexCheck++;
                // Log.d(LOG_TAG, " bufIndexCheck " + bufIndexCheck);
                if (inputBufIndex >= 0) {
                    ByteBuffer dstBuf = codecInputBuffers[inputBufIndex];

                    int sampleSize =
                            extractor.readSampleData(dstBuf, 0 /* offset */);

                    long presentationTimeUs = 0;

                    if (sampleSize < 0) {
                        Lg.d("while", "saw input EOS.");
                        sawInputEOS = true;
                        sampleSize = 0;
                    } else {
                        presentationTimeUs = extractor.getSampleTime();
                    }
                    // can throw illegal state exception (???)

                    codec.queueInputBuffer(
                            inputBufIndex,
                            0 /* offset */,
                            sampleSize,
                            presentationTimeUs,
                            sawInputEOS ? MediaCodec.BUFFER_FLAG_END_OF_STREAM : 0);

                    if (!sawInputEOS) {
                        extractor.advance();
                    }
                } else {
                    Lg.e(TAG, "inputBufIndex " + inputBufIndex);
                }
            }

            // decode to PCM and push it to the AudioTrack player
            // 解码数据为PCM
            int res = codec.dequeueOutputBuffer(info, kTimeOutUs);

            //todo add resource
//            if (res >= 0) {
//                //Log.d(LOG_TAG, "got frame, size " + info.size + "/" + info.presentationTimeUs);
//                if (info.size > 0) {
//                    noOutputCounter = 0;
//                }
//
//                int outputBufIndex = res;
//                ByteBuffer buf = codecOutputBuffers[outputBufIndex];
//
//                final byte[] chunk = new byte[info.size];
//                buf.get(chunk);
//                buf.clear();
//                if (chunk.length > 0) {
//                    //播放
//                    audioTrack.write(chunk, 0, chunk.length);
//
//                    //根据数据的大小为把byte合成short文件
//                    //然后计算音频数据的音量用于判断特征
//                    short[] music = (!isBigEnd()) ? byteArray2ShortArrayLittle(chunk, chunk.length / 2) :
//                            byteArray2ShortArrayBig(chunk, chunk.length / 2);
//                    sendData(music, music.length);
//                    calculateRealVolume(music, music.length);
//
//                    if (this.mState != State.Playing) {
//                        mDelegateHandler.onRadioPlayerPlaybackStarted(MP3RadioStreamPlayer.this);
//                    }
//                    this.mState = State.Playing;
//                    hadPlay = true;
//                }
//                //释放
//                codec.releaseOutputBuffer(outputBufIndex, false /* render */);
//                if ((info.flags & MediaCodec.BUFFER_FLAG_END_OF_STREAM) != 0) {
//                    Lg.d(TAG, "saw output EOS.");
//                    sawOutputEOS = true;
//                }
//            } else if (res == MediaCodec.INFO_OUTPUT_BUFFERS_CHANGED) {
//                codecOutputBuffers = codec.getOutputBuffers();
//
//                Lg.d(TAG, "output buffers have changed.");
//            } else if (res == MediaCodec.INFO_OUTPUT_FORMAT_CHANGED) {
//                MediaFormat oformat = codec.getOutputFormat();
//
//                Lg.d(TAG, "output format has changed to " + oformat);
//            } else {
//                Lg.d(TAG, "dequeueOutputBuffer returned " + res);
//            }
//        }
//
//        Lg.d(TAG, "stopping...");
//
//        relaxResources(true);
//
//        this.mState = State.Stopped;
//        doStop = true;
//
//// attempt reconnect
//        if (sawOutputEOS) {
//            try {
//                if (isLoop || !hadPlay) {
//                    MP3RadioStreamPlayer.this.play();
//                }
//                return;
//            } catch (IOException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
        }

    }

    /**
     * 此计算方法来自samsung开发范例
     *
     * @param buffer   buffer
     * @param readSize readSize
     */
    protected void calculateRealVolume(short[] buffer, int readSize) {
        double sum = 0;
        double mVolume = 0;
        for (int i = 0; i < readSize; i++) {
            // 这里没有做运算的优化，为了更加清晰的展示代码
            sum += buffer[i] * buffer[i];
        }
        if (readSize > 0) {
            double amplitude = sum / readSize;
            mVolume = (int) Math.sqrt(amplitude);
        }
    }
}
